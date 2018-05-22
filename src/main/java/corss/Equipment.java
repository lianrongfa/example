package corss;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;

public class Equipment {

	private Channel channel;
	private String id;
	private InetSocketAddress address;
    private InetAddress clientUDPAddresse;
    private Integer clientUDPPort;
    
    private InetSocketAddress udpAddress;
    
    
    public String getUDPId(){
    	return clientUDPAddresse+","+clientUDPPort;
    }
	
	public Equipment(String id, Channel channel){
		this.channel = channel;
		this.id = id;
		address = null;
	}
	
	public String getId(){
		return id;
	}
	
	public Channel getChannel(){
		return channel;
	}
	
	
	public void setAddress(InetSocketAddress address){
		this.address = address;
	}
	
	
	public InetSocketAddress getAddress(){
		return address;
	}
	
	public InetAddress getIP(){
		return address.getAddress();
	}


	public InetAddress getClientUDPAddresse() {
		return clientUDPAddresse;
	}

	public void setClientUDPAddresse(InetAddress clientUDPAddresse) {
		this.clientUDPAddresse = clientUDPAddresse;
	}

	public Integer getClientUDPPort() {
		return clientUDPPort;
	}

	public void setClientUDPPort(Integer clientUDPPort) {
		if (this.clientUDPPort != clientUDPPort)
		{
			this.clientUDPPort = clientUDPPort;
			udpAddress = new InetSocketAddress(this.getClientUDPAddresse(),this.getClientUDPPort());
		}
	}

	public InetSocketAddress getUdpAddress() {
		if (udpAddress == null)
			udpAddress = new InetSocketAddress(this.getClientUDPAddresse(),this.getClientUDPPort());
		return udpAddress;
	}

	public void setUdpAddress(InetSocketAddress udpAddress) {
		this.udpAddress = udpAddress;
	}
}
