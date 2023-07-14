package services;

import exceptions.CategoryAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.CategoryRepository;
import entities.Category;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Transactional(readOnly = true)
    public Category getCategory(long id){
        return repo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories(){
        return repo.findAll();
    }

    @Transactional
    public long create(Category c) throws CategoryAlreadyExistsException {
        if(repo.existsByName(c.getName()))
            throw new CategoryAlreadyExistsException();
        return repo.save(c).getId();
    }

    @Transactional
    public boolean delete(long id){
        repo.deleteById(id);
        return true;
    }

}
