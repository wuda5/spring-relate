package com.atguigu.bean;

public class Book {
	
	private String bookName;
	private String author;
	
	
	public void myInit(){
		System.out.println("这是图书的初始化方法...");
	}
	
	public void myDestory(){
		System.out.println("这是图书的销毁方法...");
	}
	
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("book被创建");
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", author=" + author + "]";
	}
	
	

}
