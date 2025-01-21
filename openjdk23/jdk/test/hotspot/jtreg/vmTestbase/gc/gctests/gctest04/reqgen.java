/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package gc.gctests.gctest04;

import nsk.share.test.*;
import nsk.share.gc.*;

//reqgen.java

/*    stress testing
 reqgen is a subtype of Thread which generates
 request to allocate small objects ( 8 ~ 32k), short live time ( 5 ~ 10 ms)
 reqdisp is a subtype of Thread which dispatches request
 and create a livethread object to allocate the requested memory
 and simulate its life time.
 */

class bufreq
{
  int bufsz;       // memory size
  int life;        // live time of the object
  bufreq next;

  bufreq(int bufsz, int t)
  {
    this.bufsz = bufsz;
        this.life = t;
        this.next = null;
  }

  public void setnext(bufreq b)
  {
    next = b;
  }

  public bufreq getnext()
  {
    return next;
  }

  public int getsize()
  {
    return bufsz;
  }

  public int livetime()
  {
    return life;
  }
}

class queue
{
    bufreq head;
    bufreq tail;
    int limit;
    int count;

    queue(int newLimit)
    {
        head = null;
        tail = null;
        limit = newLimit;
        count = 0;
    }

  public boolean okToContinue()
  {
        return (count < limit);
  }

  public synchronized void append(bufreq b)
  {
    count++;
    if ( tail == null ) // head must be null too
    {
            head = tail = b;
        return;
    }
    tail.setnext(b);
    tail = b;
  }

  public synchronized bufreq remove()
  {
    if ( head == null ) return null;
    bufreq  buf = head;
    head = head.getnext();
    if ( head == null )  // only one element in the queue
    {
            tail = head = null;
    }
    return buf;
  }
}

class  reqgen extends Thread {
  queue    req;
  int      maxsz;
  int      minsz;
  int      maxlive;
  int      minlive;
  int     amda;

  reqgen(queue req, int t)
  {
    this.req = req;
    amda = t;
  }

  public void setsize(int s1, int s2)
  {
       maxsz = s2;
       minsz = s1;
  }

  public void setlive(int t1, int t2)
  {
    maxlive = t2;
    minlive = t1;
  }

  public void run()
  {
    bufreq  buf;
    int sz;
    int t;

    sz = minsz;
    t =  minlive;
    while ( req.okToContinue() )
    {
        buf = new bufreq(sz, t);

        sz = ( 2*sz);

            if ( sz > maxsz)
            {
                sz = minsz;
        }

        t = ( 2 * t );
            if ( t >  maxlive)
            {
            t = minlive;
        }

            req.append(buf);

        try
        {
                sleep(amda);
        }
        catch(InterruptedException e) {}
      }
  }

  public bufreq nextreq()
  {
    return req.remove();
  }

}

// buffer request dispatcher and allocator
class  reqdisp extends Thread {
  queue req;

  reqdisp(queue q )
  {
    req = q;
  }

  public void run()
  {
    bufreq r;
    livethread lt;

    while ( req.okToContinue() )
    {
            r = req.remove();
        if ( r != null )
            {
                lt = new livethread(r);
            lt.start();
        }
      // simulate the interarrival time
        try
        {
            sleep((int)(LocalRandom.random() * 20));
        }
        catch (InterruptedException e) {}
    }
  }


}

class livethread extends Thread {
  bufreq req;

  livethread(bufreq r)
  {
    req = r;
  }

  public void run()
  {
    int buf[];

    buf =  new int[req.getsize()];

    // simulate the life time of the created object
    // if live time is 0, that means forever
    if ( req.livetime() == 0 )
    {
       while ( true )
       {
          try
          {
             sleep(10000);
          }
          catch (InterruptedException e) {}
       }
    }
    else
    {
       try
       {
          sleep(req.livetime());
       }
       catch (InterruptedException e) {}

    }
    // live object is outdated, should be GC'ed
  }
}
