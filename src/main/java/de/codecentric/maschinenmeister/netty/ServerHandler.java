package de.codecentric.maschinenmeister.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	
    	//hook in for the FSM, deal probably with https://netty.io/4.1/api/io/netty/handler/codec/mqtt/package-summary.html api
    	
    	
    	System.out.println();
    	System.out.println("received: "+msg.toString());
    	
    	if(msg instanceof MqttConnectMessage) {
    		
    		MqttConnectMessage conMsg = (MqttConnectMessage) msg;
    		ctx.write(new MqttConnAckMessage(new MqttFixedHeader(MqttMessageType.CONNACK, false, conMsg.fixedHeader().qosLevel(), false, 2), new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false)));
    	} else if(msg instanceof MqttPublishMessage) {
    		
    		MqttPublishMessage conMsg = (MqttPublishMessage) msg;
    		//payload -> conMsg.payload().
    		ctx.write(new MqttPubAckMessage(new MqttFixedHeader(MqttMessageType.PUBACK, false, conMsg.fixedHeader().qosLevel(), false, 2), MqttMessageIdVariableHeader.from(1)));
    	}
    	//...
    	//...
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
