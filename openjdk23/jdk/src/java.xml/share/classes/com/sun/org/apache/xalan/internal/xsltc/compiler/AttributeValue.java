/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;


/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
abstract class AttributeValue extends Expression {

    public static final AttributeValue create(SyntaxTreeNode parent,
                                              String text, Parser parser) {

        AttributeValue result;
        if (text.indexOf('{') != -1) {
            result = new AttributeValueTemplate(text, parser, parent);
        }
        else if (text.indexOf('}') != -1) {
            result = new AttributeValueTemplate(text, parser, parent);
        }
        else {
            result = new SimpleAttributeValue(text);
            result.setParser(parser);
            result.setParent(parent);
        }
        return result;
    }
}
