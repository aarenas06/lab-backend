package com.tutorial;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;

public class Handler implements RequestHandler<Request, Object> {

    @Override
    public Object handleRequest(Request request, Context context) {
        AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper=new DynamoDBMapper(db);
        Registros registros=null;

        switch (request.getHttpMethod()){
            case "GET":
                if (request.getId()== 0 ){
                    List <Registros> registros1 =new ArrayList<>();
                    registros1=mapper.scan(Registros.class,new DynamoDBScanExpression());
                    return registros1;
                }else {
                    registros= mapper.load(Registros.class, request.getId());
                    return registros;
                }
            case "POST":
                registros =request.getRegistros();
                mapper.save(registros);
                return registros;
            case"DELETE":
                registros=mapper.load(Registros.class,request.getId());
                mapper.delete(registros);
                return registros;
        }
        return null;
    }
}
