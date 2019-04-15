import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MongodbTest {
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Before
    public void init(){
        client = new MongoClient("192.168.200.68");
        database = client.getDatabase("spitdb");
        collection = database.getCollection("spit");
    }

    @Test
    public void testSimpleFind() {
        FindIterable<Document> documents = collection.find();
        documents.forEach((Consumer<Document>) document -> {
                    System.out.println(document.getString("_id"));
                    System.out.println(document.getString("content"));
                    System.out.println(document.getString("userid"));
                    System.out.println(document.getString("nickname"));
                    System.out.println(document.getInteger("visits"));
                }
        );
    }

    @Test
    public void testComplexFind() {
        BasicDBObject basonId = new BasicDBObject("userid","1013");
        BasicDBObject basonVisits = new BasicDBObject("visits",new BasicDBObject("$gt",1000));
        FindIterable<Document> documents = collection.find(basonVisits).limit(2);
        documents.forEach((Consumer<Document>) document -> {
                    System.out.println(document.getString("_id"));
                    System.out.println(document.getString("content"));
                    System.out.println(document.getString("userid"));
                    System.out.println(document.getString("nickname"));
                    System.out.println(document.getInteger("visits"));
                }
        );
    }

    @Test
    public void testInsert(){
        Document document = new Document("content","java代码插入测试文本");
        collection.insertOne(document);
    }

    @Test
    public void testUpdate(){
        Document document = new Document("$set",new BasicDBObject("content","java update"));

        collection.updateOne(new BasicDBObject("_id","5"),document);
    }

    @After
    public void onFinish(){
        client.close();
    }
}
