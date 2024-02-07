
from flask import Flask
import psycopg2
from psycopg2.extras import RealDictCursor
import os

app = Flask(__name__)

# Environment variables for DB connection
DB_HOST = os.environ.get("DB_HOST", "localhost")
DB_NAME = os.environ.get("DB_NAME", "postgres")
DB_USER = os.environ.get("DB_USER", "postgres")
DB_PASS = os.environ.get("DB_PASS", "password")

@app.route('/success')
def check_db():
    try:
        conn = psycopg2.connect(host=DB_HOST, database=DB_NAME, user=DB_USER, password=DB_PASS, cursor_factory=RealDictCursor)
        # If connection is successful
        conn.close() # Close the connection as we just want to check connectivity
        return "Well done :)", 200
    except Exception as e:
        return "Error in connection", 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)