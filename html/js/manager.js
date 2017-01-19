var socket = new WebSocket("ws://" + window.location.hostname + ":1899");

var dictionary = {}; 
var tags = {};

$().get('define.json', function(data, status){
	dictionary = JSON.parse(data);
	console.log("dict loaded");
	riot.compile(function(){
		console.log("compiling");
		for(var key in dictionary.client){
			$("body").htmlAppend("<" + key +" id=" + key +"></" + key + ">");
			tags[key] = riot.mount("#"+key)[0];
			tags[key].values = {client:dictionary.client[key]};
			tags[key].name = key;
			tags[key].sendChange = function(change){
					console.log("Send change called: client");
					var obj = {
						type:"delta",
					};
					obj[key] = change;
					socket.send(JSON.stringify(obj));
			}
			tags[key].update();
		}
		for(var key in dictionary.server){
			if(key in tags){
				tags[key].values.server = dictionary.server[key];
			}else{
				$("body").htmlAppend("<" + key +" id=" + key +"></" + key + ">");
				tags[key] = riot.mount("#"+key)[0];
				tags[key].values = {server:dictionary.server[key]};
				tags[key].name = key;
				tags[key].sendChange = function(change){
					console.log("Send change called: server");
					var obj = {
						type:"delta",
					};
					obj[key] = change;
					socket.send(JSON.stringify(obj));
				}
				tags[key].update();
			}
		}
	});
	
}, true);

function mapVals(dict){
	var temp = [];
	for(var map in dict){
		temp[map] = dict[map].value;
	}
	return temp;
}
function put(name, stringOrDict, valueMebbe){
	if(typeof name != "string"){
		if(stringOrDict == undefined && valueMebbe == undefined){
			name.type = "delta";
			socket.send(JSON.stringify(name));
		}
		else //Throw an error here.
			return "WTFFFF";
		return;
	}
	
	var message = {
		type:"delta"
	}
	
	if(typeof stringOrDict == "string"){
		message[name] = {};
		message[name][stringOrDict] = valueMebbe;
	}else{
		message[name] = stringOrDict;
	}
	socket.send(JSON.stringify(message));
}


socket.onmessage = function(data){
	data = JSON.parse(data.data);
	console.log(data);
	if(data.type == "error"){
		//deal with this later
		console.log(data);
		return;
	}
	var delta;
	var pointer;
	if(data.type=="delta"){
		delta=true;
		pointer = "client";
	}else if(data.type=="confirm"){
		delta=false;
		pointer = "server";
	}else{
		error("malformed!");
		return;
	}
	for(var key in data){
		if(key == "type"){
			continue;
		}
		if(dictionary[pointer][key]){
			for(var pair in data[key]){
				if(!dictionary[pointer][key][pair]){
					error("wrong permissions or doesn't exist!");
					return;
				}
			}
		}else{
			error("wrong permissions or doesn't exist!");
			return;
		}
	}
	if(delta){
		data.type = "confirm";
		socket.send(JSON.stringify(data));
	}
	for(var key in data){
		if(key == "type"){
			continue;
		}
		for(var pair in data[key]){
			dictionary[pointer][key][pair] = data[key][pair];
		}
		tags[key].values[pointer] = dictionary[pointer][key];
		tags[key].update();
	}
}
function testData(message){
	socket.onmessage({data:JSON.stringify(message)});
}
function error(message){
	console.log("Error: " + message);
	socket.send(JSON.stringify({type:"error",message:message}));
}

