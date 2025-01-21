#  StarshipOS Copyright (c) 2025. R.A. James

from fastapi import FastAPI

app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World"}
