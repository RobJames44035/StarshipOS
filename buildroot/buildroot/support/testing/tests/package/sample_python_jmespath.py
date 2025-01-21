#  StarshipOS Copyright (c) 2025. R.A. James

import jmespath
expression = jmespath.compile('foo.bar')
res = expression.search({'foo': {'bar': 'baz'}})
assert res == "baz", "expression.search failed"
