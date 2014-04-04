package com.gwinsoft.components2.job;

import java.text.NumberFormat;
public class Processor {
	/**
	 * 在存储Processor实例时用的key值
	 */
	public static final String PROCESSOR_STORAGE_KEY="PROCESSOR_STORAGE_KEY";
	private static final NumberFormat nf  =  NumberFormat.getPercentInstance();
	static{
		nf.setMinimumFractionDigits(2);
	}
	private int total=100;
	private int process=0;
	private int interval=1;
	private String percentageText="0.00%";
	private double percentage=0.00;
	private boolean complete=false;
	private boolean hasSetTotal=false;
	private boolean hasSetInterval=false;
	
	public Processor(int total, int process, int interval) {
		super();
		this.setTotal(total);
		this.setProcess(process);
		this.setInterval(interval);
	}
	
	public Processor(int total, int process) {
		super();
		this.setTotal(total);
		this.setProcess(process);
	}

	public Processor() {
		super();
	}

	/**
	 * @return Returns the complete.
	 */
	public boolean isComplete() {
		return complete;
	}
	/**
	 * @param complete The complete to set.
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	/**
	 * @return Returns the percentage.
	 */
	public double getPercentage() {
		return percentage;
	}
	/**
	 * @param percentage The percentage to set.
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
		this.setPercentageText(nf.format(percentage));
		if(percentage>=1){
			this.setComplete(true);
		}
	}
	/**
	 * @return Returns the percentageText.
	 */
	public String getPercentageText() {
		return percentageText;
	}
	/**
	 * @param percentageText The percentageText to set.
	 */
	public void setPercentageText(String percentageText) {
		this.percentageText = percentageText;
	}
	/**
	 * @return Returns the process.
	 */
	public int getProcess() {
		return process;
	}
	/**
	 * @param process The process to set.
	 */
	public void setProcess(int process) {
		if(process<0){
			throw new IllegalArgumentException("进度数必须大于等于0！");
		}
		this.process = process;
		this.calculatePercentage();
	}
	/**
	 * @return Returns the total.
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(int total) {
		if(total<=0){
			throw new IllegalArgumentException("总数必须大于0！");
		}
		if(!hasSetTotal){
			this.total = total;
			hasSetTotal=true;
		}
	}
	
	/**
	 * @return Returns the interval.
	 */
	public int getInterval() {
		return interval;
	}
	/**
	 * @param interval The interval to set.
	 */
	public void setInterval(int interval) {
		if(interval<=0){
			throw new IllegalArgumentException("步进必须大于0！");
		}
		if(!hasSetInterval){
			this.interval = interval;
			hasSetInterval=true;
		}
	}
	/**
	 * 进度前进一步
	 */
	public void nextStep(){
		this.setProcess(this.getProcess()+this.getInterval());
	}
	private void calculatePercentage(){
		double result=(double)this.getProcess()/(double)this.getTotal();
		result=result>1?1:result;
		this.setPercentage(result);
		//System.out.println("process:"+this.getProcess()+",total:"+this.getTotal()+",percentage:"+this.getPercentage()+",percentageText"+this.getPercentageText());
	}
}
