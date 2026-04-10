package edu.library_management_system.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.library_management_system.entities.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	
	public Book findByTitleAndAuthor(String title,String author);
	//Select b from Book b where b.title and b.author=:author
	
	@Query("Select b from Book b where b.price=:price")
	public List<Book> getBooksByPrice(double price);
	
	
}




