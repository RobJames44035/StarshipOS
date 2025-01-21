#  StarshipOS Copyright (c) 2025. R.A. James

from cryptography.fernet import Fernet
key = Fernet.generate_key()
f = Fernet(key)
