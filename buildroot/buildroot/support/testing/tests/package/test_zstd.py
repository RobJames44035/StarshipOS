#  StarshipOS Copyright (c) 2025. R.A. James

from tests.package.test_compressor_base import TestCompressorBase


class TestZstd(TestCompressorBase):
    __test__ = True
    config = TestCompressorBase.config + \
        """
        BR2_PACKAGE_ZSTD=y
        """
    compress_cmd = "zstd"
    compressed_file_ext = ".zst"
