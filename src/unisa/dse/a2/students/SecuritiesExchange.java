package unisa.dse.a2.students;

import java.util.HashMap;
import java.util.Scanner;

import unisa.dse.a2.interfaces.ListGeneric;

public class SecuritiesExchange {

	/**
	 * Exchange name
	 */
	private String name;

	public String getName() {
		return this.name;
	}

	/**
	 * List of brokers on this exchange
	 */
	public ListGeneric<StockBroker> brokers;

	/**
	 * List of announcements of each trade processed
	 */
	public ListGeneric<String> announcements;

	/**
	 * HashMap storing the companies, stored based on their company code as the key
	 */
	public HashMap<String, ListedCompany> companies;

	/**
	 * Initialises the exchange ready to handle brokers, announcements, and
	 * companies
	 * 
	 * @param name
	 */
	public SecuritiesExchange(String name) {
		this.name = name;
		this.companies = new HashMap<String, ListedCompany>();
		this.brokers = new DSEListGeneric<StockBroker>();
		this.announcements = new DSEListGeneric<String>();

	}

	/**
	 * Adds the given company to the list of listed companies on the exchange
	 * 
	 * @param company
	 * @return true if the company was added, false if it was not
	 */
	public boolean addCompany(ListedCompany company) {
		if (company != null) {
			if (!companies.containsValue(company)) {
				companies.put(company.getCode(), company);
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the given broke to the list of brokers on the exchange
	 * 
	 * @param company
	 */
	public boolean addBroker(StockBroker broker) {
		if (broker == null) {
			return false;
		}
		if (brokers == null) {
			return brokers.add(broker);
		} else if (brokers != null && !brokers.contains(broker)) {
			return brokers.add(broker);
		}
		return false;
	}

	/**
	 * Process the next trade provided by each broker, processing brokers starting
	 * from index 0 through to the end
	 * 
	 * If the exchange has three brokers, each with trades in their queue, then
	 * three trades will processed, one from each broker.
	 * 
	 * If a broker has no pending trades, that broker is skipped
	 * 
	 * Each processed trade should also make a formal announcement of the trade to
	 * the announcements list in the form a string: "Trade: QUANTITY COMPANY_CODE @
	 * PRICE_BEFORE_TRADE via BROKERNAME", e.g. "Trade: 100 DALL @ 99 via Honest
	 * Harry Broking" for a sale of 100 DALL shares if they were valued at $99 Price
	 * shown should be the price of the trade BEFORE it's processed. Each trade
	 * should add its announcement at the end of the announcements list
	 * 
	 * @return The number of successful trades completed across all brokers
	 * @throws UntradedCompanyException when traded company is not listed on this
	 *                                  exchange
	 */
	public int processTradeRound() throws UntradedCompanyException {
		int numberOfSuccessTrade = 0;
		
		for (int index = 0; index < this.brokers.size(); index++) {
			StockBroker broker = brokers.get(index);
			if (broker.getPendingTradeCount() == 0)
				continue;
			Trade trade = broker.getNextTrade();
			int quantity = trade.getShareQuantity();
			if (!companies.containsKey(trade.getCompanyCode())) {
				throw new UntradedCompanyException(trade.getCompanyCode());
			}
			
			String companyCode = trade.getCompanyCode();
			String brokerName = trade.getStockBroker().getName();
			ListedCompany company = companies.get(companyCode);
			company.processTrade(quantity);
			int price = company.getCurrentPrice();

			announcements.add("Trade: " + quantity + " " + companyCode + " @ " + price + " via " + brokerName);
			numberOfSuccessTrade++;

		}
		return numberOfSuccessTrade;
	}

	public int runCommandLineExchange(Scanner sc) {

		return 0;
	}
}
