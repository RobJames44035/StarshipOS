#  StarshipOS Copyright (c) 2025. R.A. James

from tests.package.test_python import TestPythonPackageBase


class TestPythonPy3Ubjson(TestPythonPackageBase):
    __test__ = True
    config = TestPythonPackageBase.config + \
        """
        BR2_PACKAGE_PYTHON3=y
        BR2_PACKAGE_PYTHON_UBJSON=y
        """
    sample_scripts = ["tests/package/sample_python_ubjson_enc.py",
                      "tests/package/sample_python_ubjson_dec.py"]
