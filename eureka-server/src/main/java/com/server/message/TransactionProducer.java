package com.server.message;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 事物消息生产者
 *
 * @Date 2020/7/13 11:19
 * @name TransactionMQProducer
 */

@Service
public class TransactionProducer extends AbstractMq {

    public void send() throws Exception {
        TransactionListener listener = new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                System.out.println("executeLocalTransaction 本地消息处理");
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("checkLocalTransaction 本地事物消息回查");
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        };
        TransactionMQProducer producer = new TransactionMQProducer("TransactionGroup");
        producer.setNamesrvAddr(nameServer);
        ExecutorService executorService = Executors.newCachedThreadPool();
        producer.setExecutorService(executorService);
        producer.setTransactionListener(listener);
        producer.setInstanceName("TransactionProducer");
        producer.start();

        //
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTransaction", tags[i % tags.length], "KEYTwo" + i, ("HelloMessage" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(10);
        }


    }


}
