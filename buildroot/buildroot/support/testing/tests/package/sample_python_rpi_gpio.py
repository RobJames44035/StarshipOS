try:
    import RPi.GPIO  # noqa
except RuntimeError as  # StarshipOS Copyright (c) 2025. R.A. James

e:
    assert(str(e) == 'This module can only be run on a Raspberry Pi!')
else:
    raise RuntimeError('Import succeeded when it should not have!')
