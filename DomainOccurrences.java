import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author Githu Elsa George
 * @since 16.08.2017
 * 
 * Java Class for counting the number of occurrences of
 * different domains from the links in a given URL/Webpage 
 *
 */
public class DomainOccurrences {

	/**
	 * This is the main method which makes use of extractLinks and getDomainName methods.
	 * @param args Unused.
	 * @return Nothing.
	 */
	public static void main(String[] args) {
		InputStream is = null;
		BufferedReader br;
		String line,domain;
		int linksCount=1;

		try {
			DomainOccurrences domainOccurrences = new DomainOccurrences();
			/* Reading the URL of the Webpage */
			System.out.println("Please enter the URL of the Webpage: ");
			Scanner scannedUrl = new Scanner(System.in);
			String url = scannedUrl.nextLine();

			/* Method call to get all the links in the webpage */
			List<String> links = domainOccurrences.extractLinks(url);

			/* Populating the HashMap with domain name and its count */
			HashMap<String,Integer> domainMap = new HashMap<String,Integer>();
			System.out.println("List of all available links in the given URL::");
			for (String link : links) { 
				System.out.println(linksCount+". "+link);
				linksCount++;
				/* Method call to get the domain name from the link*/
				domain = domainOccurrences.getDomainName(link);
				if(null!=domain && !(domain.isEmpty())){
					if(domainMap.containsKey(domain)){
						int count = domainMap.get(domain);
						domainMap.put(domain, count+1);
					}
					else{
						domainMap.put(domain, 1);
					}
				}
			}
			/* Populating the HashMap with domain name and its count -- ends*/

			/* Displaying the required output */
			System.out.println("List of domains with the number of occurrences:");
			Iterator it = domainMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<Integer,String> thisEntry = (Entry)it.next();
				Object key = thisEntry.getKey();
				Object value = thisEntry.getValue();
				System.out.println(key+" - "+value);
			} /* Displaying the required output -- ends*/
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		} 
		finally {
			try {
				if (is != null) is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * This is the method which extract the links from a given webpage.
	 * @param String which is the url of the webpage.
	 * @return List of String which are the links extracted from the webpage.
	 * @exception Exception.
	 * 
	 */
	public static List<String> extractLinks(String url) throws Exception { 
		final ArrayList<String> result = new ArrayList<String>(); 
		Document doc = Jsoup.connect(url).get(); 
		Elements links = doc.select("a[href]"); 
		for (Element link : links) { 
			result.add(link.attr("abs:href")); 
		} 
		return result; 
	}

	/**
	 * This is the method which get the domain name from a link.
	 * @param String which is the url of a link in the webpage.
	 * @return String which is the domain name of the link.
	 * @exception MalformedURLException.
	 * 
	 */
	public static String getDomainName(String link) throws MalformedURLException{
		String domain = "";
		if(null!=link && !(link.isEmpty())){
			if(!link.startsWith("http") && !link.startsWith("https")){
				link = "http://" + link;
			}        
			URL netUrl = new URL(link);
			domain = netUrl.getHost();
			if(domain.startsWith("www")){
				domain = domain.substring("www".length()+1);
			}
		}
		return domain;
	}
}
