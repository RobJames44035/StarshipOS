/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

var moduleSearchIndex;
var packageSearchIndex;
var typeSearchIndex;
var memberSearchIndex;
var tagSearchIndex;

var clargs = arguments;
var search;

function loadIndexFiles(docsPath) {
    tryLoad(docsPath, "module-search-index.js");
    tryLoad(docsPath, "package-search-index.js");
    tryLoad(docsPath, "type-search-index.js");
    tryLoad(docsPath, "member-search-index.js");
    tryLoad(docsPath, "tag-search-index.js");
    load(docsPath + "/script-files/search.js");
}

function tryLoad(docsPath, file) {
    try {
        load(docsPath + "/" + file);
    } catch (e) {
        print(e);
    }
}

var updateSearchResults = function() {};

function indexFilesLoaded() {
    return moduleSearchIndex
        && packageSearchIndex
        && typeSearchIndex
        && memberSearchIndex
        && tagSearchIndex;
}

// jquery mock functions
var $ = function(f) {
    if (typeof f === "function") {
        f();
    } else {
        return {
            attr: function() {
                return this;
            },
            css: function() {
                return this;
            },
            val: function() {
                return this;
            },
            prop: function() {
                return this;
            },
            addClass: function() {
                return this;
            },
            each: function() {
                return this;
            },
            removeClass: function() {
                return this;
            },
            on: function() {
                return this;
            },
            focus: function() {
                return this;
            },
            blur: function() {
                return this;
            },
            click: function() {
                return this;
            },
            hover: function() {
                return this;
            },
            catcomplete: function(o) {
                o.close = function() {};
                search = function(term) {
                    var resultList = null;
                    o.source({
                            term: term
                        },
                        function(result) {
                            resultList = renderMenu(result);
                        }
                    );
                    return resultList;
                };
                for (var i = 0; i < clargs.length; i++) {
                    search(clargs[i]);
                }
            },
            "0": {
                setSelectionRange: function() {
                    return this;
                }
            }
        }
    }
};

$.each = function(arr, f) {
    for (var i = 0; i < arr.length; i++) {
        f(i, arr[i]);
    }
};

$.widget = function(a, b, c) {
};

$.ui = {
    autocomplete: {
        escapeRegex: function(re) {
            return re.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\$&');
        }
    }
};

var console = {
    log: function() {
        print.apply(this, arguments);
    }
};

var window = {
    innerWidth: 800
}

var renderMenu = function(items) {
    var result = new java.util.ArrayList();
    var currentCategory = "";
    $.each(items, function(index, item) {
        var li;
        if (item.l !== messages.noResult && item.category !== currentCategory) {
            currentCategory = item.category;
        }
        result.add(renderItem(item));
    });
    return result;
};

var renderItem = function(item) {
    return item.l || item.input;
};


