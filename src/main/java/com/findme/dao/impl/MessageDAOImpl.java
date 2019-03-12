package com.findme.dao.impl;

import com.findme.dao.MessageDAO;
import com.findme.model.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

@Log4j
@Repository
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {

}
