package com.example.figuremall_refact.repository.iceRinkRepository;

import com.example.figuremall_refact.domain.iceRink.IceRink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IceRinkRepository extends CrudRepository<IceRink,Long> {
}
