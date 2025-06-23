from pymongo import MongoClient, ASCENDING
from pymongo.errors import PyMongoError
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations with MongoDB, now enhanced with indexing, transactions, and analytics support. """

    def __init__(self, user, password, host, port, db, col):
        self.client = MongoClient(f'mongodb://{user}:{password}@{host}:{port}')
        self.database = self.client[db]
        self.collection = self.database[col]
        self._create_indexes()

    def _create_indexes(self):
        """ Ensure indexes on important fields for performance """
        self.collection.create_index([('animal_type', ASCENDING)])
        self.collection.create_index([('age', ASCENDING)])
        self.collection.create_index([('breed', ASCENDING)])
        print("Indexes created or verified.")

    def create(self, data):
        if data:
            try:
                result = self.collection.insert_one(data)
                return True if result.inserted_id else False
            except PyMongoError as e:
                print(f"Insertion error: {e}")
                return False
        else:
            raise Exception("Nothing to save because data parameter is empty")

    def read(self, query):
        try:
            cursor = self.collection.find(query)
            return list(cursor)
        except PyMongoError as e:
            print(f"Read error: {e}")
            return []

    def update(self, query_filter, update_fields):
        try:
            result = self.collection.update_many(query_filter, {'$set': update_fields})
            return result.modified_count
        except PyMongoError as e:
            print(f"Update error: {e}")
            return 0

    def delete(self, query_filter):
        try:
            result = self.collection.delete_many(query_filter)
            return result.deleted_count
        except PyMongoError as e:
            print(f"Delete error: {e}")
            return 0

    def batch_insert_with_transaction(self, data_list):
        """ Insert multiple documents with transactional safety """
        with self.client.start_session() as session:
            try:
                with session.start_transaction():
                    self.collection.insert_many(data_list, session=session)
                    print("Transaction successful.")
                    return True
            except PyMongoError as e:
                print(f"Transaction failed: {e}")
                return False

    def get_analytics_summary(self):
        """ Example aggregation for dashboard: count by type and average age by breed """
        try:
            summary = list(self.collection.aggregate([
                {
                    "$group": {
                        "_id": "$animal_type",
                        "count": {"$sum": 1}
                    }
                }
            ]))

            avg_age = list(self.collection.aggregate([
                {
                    "$group": {
                        "_id": "$breed",
                        "average_age": {"$avg": "$age"}
                    }
                }
            ]))

            return {
                "count_by_type": summary,
                "average_age_by_breed": avg_age
            }
        except PyMongoError as e:
            print(f"Aggregation error: {e}")
            return {}

# Example usage
if __name__ == "__main__":
    USER = 'aacuser'
    PASS = 'Password1234'
    HOST = 'nv-desktop-services.apporto.com'
    PORT = 30372
    DB = 'AAC'
    COL = 'animals'

    shelter = AnimalShelter(USER, PASS, HOST, PORT, DB, COL)

    # Insert with transaction
    shelter.batch_insert_with_transaction([
        {'name': 'Toby', 'animal_type': 'Dog', 'age': 3, 'breed': 'Beagle'},
        {'name': 'Nala', 'animal_type': 'Cat', 'age': 2, 'breed': 'Siamese'}
    ])

    # Dashboard Analytics
    dashboard_data = shelter.get_analytics_summary()
    print("Dashboard Summary:", dashboard_data)
