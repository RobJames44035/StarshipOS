/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPathContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * This class overrides the XNodeSet#object() method to provide the original
 * Node object, NodeList object, or NodeIterator.
 */
public class XNodeSetForDOM extends XNodeSet
{
    static final long serialVersionUID = -8396190713754624640L;
  @SuppressWarnings("serial") // Type of field is not Serializable
  Object m_origObj;

  public XNodeSetForDOM(Node node, DTMManager dtmMgr)
  {
    m_dtmMgr = dtmMgr;
    m_origObj = node;
    int dtmHandle = dtmMgr.getDTMHandleFromNode(node);
    setObject(new NodeSetDTM(dtmMgr));
    ((NodeSetDTM) m_obj).addNode(dtmHandle);
  }

  /**
   * Construct a XNodeSet object.
   *
   * @param val Value of the XNodeSet object
   */
  public XNodeSetForDOM(XNodeSet val)
  {
        super(val);
        if(val instanceof XNodeSetForDOM)
        m_origObj = ((XNodeSetForDOM)val).m_origObj;
  }

  public XNodeSetForDOM(NodeList nodeList, XPathContext xctxt)
  {
    m_dtmMgr = xctxt.getDTMManager();
    m_origObj = nodeList;

    // JKESS 20020514: Longer-term solution is to force
    // folks to request length through an accessor, so we can defer this
    // retrieval... but that requires an API change.
    // m_obj=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeList, xctxt);
    com.sun.org.apache.xpath.internal.NodeSetDTM nsdtm=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeList, xctxt);
    m_last=nsdtm.getLength();
    setObject(nsdtm);
  }

  public XNodeSetForDOM(NodeIterator nodeIter, XPathContext xctxt)
  {
    m_dtmMgr = xctxt.getDTMManager();
    m_origObj = nodeIter;

    // JKESS 20020514: Longer-term solution is to force
    // folks to request length through an accessor, so we can defer this
    // retrieval... but that requires an API change.
    // m_obj = new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeIter, xctxt);
    com.sun.org.apache.xpath.internal.NodeSetDTM nsdtm=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeIter, xctxt);
    m_last=nsdtm.getLength();
    setObject(nsdtm);
  }

  /**
   * Return the original DOM object that the user passed in.  For use primarily
   * by the extension mechanism.
   *
   * @return The object that this class wraps
   */
  public Object object()
  {
    return m_origObj;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeIterator nodeset() throws javax.xml.transform.TransformerException
  {
    return (m_origObj instanceof NodeIterator)
                   ? (NodeIterator)m_origObj : super.nodeset();
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeList nodelist() throws javax.xml.transform.TransformerException
  {
    return (m_origObj instanceof NodeList)
                   ? (NodeList)m_origObj : super.nodelist();
  }



}
