from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, user, password, host, port, db, col):
        """ Initialize the MongoClient and connect to the specified database and collection. """
        self.client = MongoClient(f'mongodb://{user}:{password}@{host}:{port}')
        self.database = self.client[db]
        self.collection = self.database[col]

    def create(self, data):
        """ Insert a document into the specified MongoDB database and collection. Return True if successful insert, else False. """
        if data is not None:
            result = self.collection.insert_one(data)
            return True if result.inserted_id else False
        else:
            raise Exception("Nothing to save because data parameter is empty")

    def read(self, query):
        """ Query for documents from the specified MongoDB database and collection. Return the result in a list if the command is successful, else an empty list. """
        cursor = self.collection.find(query)
        result = [document for document in cursor]
        return result
    
    def update(self, update_data):
        """ Query for documents to update from the specified MongoDB database and collection. Return the result in a list if the command is successful, else False. """
        if update_data is not None:
                result = self.collection.insert_one(update_data)
                return result
        else:
            raise Exception("Unable to update, save incomplete")
            
    def delete(self, delete_data):
        """ Query to remove from the specified MongoSB database and collection. Return True if succesful deletion, else False. """
        if delete_data is not None:
            if delete_data:
                result = self.collection.delete_one(delete_data)
        else:
            raise Exception("Nothing to delete because data parameter is empty.")

# Example Usage
if __name__ == "__main__":
    # Connection Variables
    USER = 'aacuser'
    PASS = 'Password1234'
    HOST = 'nv-desktop-services.apporto.com'
    PORT = 30372
    DB = 'AAC'
    COL = 'animals'

    # Instantiate the AnimalShelter class
    animal_shelter = AnimalShelter(USER, PASS, HOST, PORT, DB, COL)

    # Example data to insert
    data_to_insert = {
        'name': 'Mr Meowmitkins',
        'animal_type': 'Cat',
        'age': 5,
        'breed': 'Persian'
    }
    
    # Example data to read
    data_to_read = {
        'name': 'Mr Meowmitkins',
        'animal_type': 'Cat',
        'age': 5,
        'breed': 'Persian'
    }
    
    # Example data to update
    data_to_update = {
        'name': 'Mr Meowmitkins',
        'animal type': 'Cat',
        'age': 6,
        'breed': 'Persian'
    }
    
    # Example data to delete
    data_to_delete = {
        'name': 'Mr. Meowmitkins',
        'animal type': 'Cat',
        'age': 6,
        'breed': 'Persian'
    }
    
    # Test create method
    insert_result = animal_shelter.create(data_to_insert)
    print(f"Insert Result: {insert_result}")

    # Test read method
    query_criteria = {'name': 'Mr Meowmitkins'}
    read_result = animal_shelter.read(query_criteria)
    print(f"Read Result: {read_result}")
    
    # Test update method
    query_criteria = { 'name': 'Mr. Meowmitkins'}
    update_data = { 'age': 6}
    update_result = animal_shelter.update(update_data)
    print(f"Update Result: {update_data}")
    
    # Test delete method
    query_criteria = { 'name': 'Mr. Meowmitkins'}
    delete_result = animal_shelter.delete(data_to_delete)
    print(f"Delete Result: {delete_result}")