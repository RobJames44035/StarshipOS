#  StarshipOS Copyright (c) 2025. R.A. James

from flask import Flask
app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello, World!'
