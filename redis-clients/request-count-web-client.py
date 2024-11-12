
from flask import Flask
import redis
from flask_caching import Cache
import time
import random

app = Flask(__name__)

# Configure Flask-Caching with Redis
cache = Cache(app, config={'CACHE_TYPE': 'RedisCache', 'CACHE_REDIS_URL': 'redis://localhost:6379/0'})

# Initialize Redis client
redis_client = redis.StrictRedis(host='localhost', port=6379, db=0, decode_responses=True)

@app.route('/home')
def home():
    # Increment request count using INCR command in Redis
    req_count = redis_client.incr('reqCount')
    return f"Hello from Demo Service. Request Count: {req_count}"

@app.route('/data')
@cache.cached(timeout=60, key_prefix='dataCache')
def get_data():
    # Simulate a delay to represent a slow data source
    time.sleep(0.5)  # 500 ms delay
    return f"Sample Data {random.random()}"

if __name__ == '__main__':
    app.run(debug=True)
