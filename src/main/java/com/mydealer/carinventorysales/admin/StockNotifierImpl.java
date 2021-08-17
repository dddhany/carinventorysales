package com.mydealer.carinventorysales.admin;

public class StockNotifierImpl implements StockNotifier {
	
	private static final int NOTIFY_STOCK_THRESHOLD = 2;

	@Override
	public void notifyLowStock(int stockLevel) {
		if(stockLevel<NOTIFY_STOCK_THRESHOLD) {
			System.out.format("Notify store admin on low stock: %d < %d.\n", stockLevel, NOTIFY_STOCK_THRESHOLD);
		}
	}

}
