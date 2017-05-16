package self.ankit.satori;

import com.satori.rtm.*;
import com.satori.rtm.model.*;
import java.util.concurrent.*;


public class SatoriEx {
	
	  static final String endpoint = "wss://open-data.api.satori.com";
	  static final String appkey = "C6cf6bC9FFb97e3eaB0FFDC1C2fE5DD1";
	  static final String channel = "Singapore-Traffic-images";
	  public static void main(String[] args) throws Exception {
	    final RtmClient client = new RtmClientBuilder(endpoint, appkey)
	        .setListener(new RtmClientAdapter() {
	          @Override
	          public void onEnterConnected(RtmClient client) {
	            System.out.println("Connected to RTM!");
	          }
	        })
	        .build();

	    final CountDownLatch success = new CountDownLatch(1);

	    SubscriptionListener listener = new SubscriptionAdapter() {
	      @Override
	      public void onSubscriptionData(SubscriptionData data) {
	        for (AnyJson json : data.getMessages()) {
	          System.out.println("Got message: " + json);
	        }
	        success.countDown();
	      }
	    };

	    client.createSubscription(channel, SubscriptionMode.SIMPLE, listener);

	    client.start();

	    success.await(50, TimeUnit.SECONDS);

	    client.shutdown();
	  }
	}

