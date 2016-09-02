/* SocketImpl.java -- Abstract socket implementation class
   Copyright (C) 1998, 1999, 2000, 2001 Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

As a special exception, if you link this library with other files to
produce an executable, this library does not by itself cause the
resulting executable to be covered by the GNU General Public License.
This exception does not however invalidate any other reasons why the
executable file might be covered by the GNU General Public License. */

package java.net;

import java.io.*;

 /* Written using on-line Java Platform 1.2 API Specification.
  * Believed complete and correct.
  */

  /**
   * This abstract class serves as the parent class for socket implementations.
   * The implementation class serves an intermediary to native routines that
   * perform system specific socket operations.
   * <p>
   * A default implementation is provided by the system, but this can be
   * changed via installing a <code>SocketImplFactory</code> (through a call 
   * to the static method <code>Socket.setSocketImplFactory</code>).  A 
   * subclass of <code>Socket</code> can also pass in a <code>SocketImpl</code>
   * to the <code>Socket(SocketImpl)</code> constructor to use an 
   * implementation different from the system default without installing
   * a factory.
   *
   * @author Aaron M. Renn (arenn@urbanophile.com)
   * @author Per Bothner <bothner@cygnus.com>
   */
public abstract class SocketImpl implements SocketOptions
{


  /**
   * The address of the remote end of the socket connection
   */
  protected InetAddress address;

  /**
   * A FileDescriptor object representing this socket connection.  
   */
  protected FileDescriptor fd;

  /**
   * The port number the socket is bound to locally
   */
  protected int localport;

  /**
   * The port number of the remote end of the socket connection
   */
  protected int port;

  /**
   * Default, no-argument constructor for use by subclasses.
   */
  public SocketImpl ()
  {
  }

  /**
   * Creates a new socket that is not bound to any local address/port and
   * is not connected to any remote address/port.  This will be created as
   * a stream socket if the stream parameter is true, or a datagram socket
   * if the stream parameter is false.
   *
   * @param stream true for a stream socket, false for a datagram socket
   */
  protected abstract void create (boolean stream) throws IOException;

  /**
   * Connects to the remote hostname and port specified as arguments.
   *
   * @param host The remote hostname to connect to
   * @param port The remote port to connect to
   *
   * @exception IOException If an error occurs
   */
  protected abstract void connect (String host, int port) throws IOException;

  /**
   * Connects to the remote address and port specified as arguments.
   *
   * @param host The remote address to connect to
   * @param port The remote port to connect to
   *
   * @exception IOException If an error occurs
   */
  protected abstract void connect (InetAddress host, int port)
    throws IOException;

  /**
   * Binds to the specified port on the specified addr.  Note that this addr
   * must represent a local IP address.
   * <p>
   * Note that it is unspecified how to bind to all interfaces on the localhost
   * (INADDR_ANY).
   *
   * @param host The address to bind to
   * @param port The port number to bind to
   *
   * @exception IOException If an error occurs
   */
  protected abstract void bind (InetAddress host, int port) throws IOException;

  /**
   * Starts listening for connections on a socket. The backlog parameter
   * is how many pending connections will queue up waiting to be serviced
   * before being accept'ed.  If the queue of pending requests exceeds this
   * number, additional connections will be refused.
   *
   * @param backlog The length of the pending connection queue
   * 
   * @exception IOException If an error occurs
   */
  protected abstract void listen (int backlog) throws IOException;

  /**
   * Accepts a connection on this socket.
   *
   * @param s The implementation object for the accepted connection.
   *
   * @exception IOException If an error occurs
   */
  protected abstract void accept (SocketImpl s) throws IOException;

  /**
   * Returns an <code>InputStream</code> object for reading from this socket.
   *
   * @return An <code>InputStream</code> for reading from this socket.
   *
   * @exception IOException If an error occurs
   */
  protected abstract InputStream getInputStream() throws IOException;

  /**
   * Returns an <code>OutputStream</code> object for writing to this socket
   * 
   * @return An <code>OutputStream</code> for writing to this socket.
   *
   * @exception IOException If an error occurs.
   */
  protected abstract OutputStream getOutputStream() throws IOException;

  /**
   * Returns the number of bytes that the caller can read from this socket
   * without blocking.
   *
   * @return The number of readable bytes before blocking
   *
   * @exception IOException If an error occurs
   */
  protected abstract int available () throws IOException;

  /**
   * Closes the socket.  This will normally cause any resources, such as the
   * InputStream, OutputStream and associated file descriptors  to be freed.
   * <p>
   * Note that if the SO_LINGER option is set on this socket, then the
   * operation could block.
   *
   * @exception IOException If an error occurs
   */
  protected abstract void close () throws IOException;

  /**
   * Returns the FileDescriptor objects for this socket.
   *
   * @return A FileDescriptor for this socket.
   */
  protected FileDescriptor getFileDescriptor () { return fd; }

  /**
   * Returns the remote address this socket is connected to
   *
   * @return The remote address
   */
  protected InetAddress getInetAddress () { return address; }

  /**
   * Returns the remote port this socket is connected to
   *
   * @return The remote port
   */
  protected int getPort () { return port; }

  /**
   * Returns the local port this socket is bound to
   *
   * @return The local port
   */
  protected int getLocalPort () { return localport; }

  /**
   * Returns a <code>String</code> representing the remote host and port of this
   * socket.
   *
   * @return A <code>String</code> for this socket.
   */
  public String toString ()
  {
    return "[addr=" + address.toString() + ",port=" + Integer.toString(port) +
      ",localport=" + Integer.toString(localport) + "]";
  }
}