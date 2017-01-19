
<inputs>
<div class="flex-container">
<h2> Inputs </h2>
	<div  class="flex-item" id="inputitem" each={ inputValues }>
		<p class="wait">

		<form>
		<h3>{ title }</h3>
			<input type="range" name="valueEdit1" id="rangeInput" min="{ min }" max="{ max }" value ="{ value }"  oninput="textInput.value = rangeInput.value;submitter.style.display= 'initial'" class="slider"></input>
			<input type="text" name="valueEdit1" id="textInput" placeholder="{ value }" oninput="rangeInput.value = textInput.value;submitter.style.display= 'initial'"></input>
			<input type="button" id="submitter" name="sub1" class="submitVal" value="submit" onclick={ parent.getVal }></input>
		</form>
		</p>
	</div>
	</div>

    <script type="text/javascript" src="./define.json"></script>
	 <script>
	this.inputValues = [];
	
	this.on('update', function() {
		console.log("update");
		if(!this.values) return;
		this.inputValues = [];
	    for(var value in this.values.server){
    	    console.log(value + " has " + this.values.server[value]["value"]);
        	this.inputValues.push({
        		"title": value,
        		"max": this.values.server[value]["max"],
        		"min": this.values.server[value]["min"],
        		"value": this.values.server[value]["value"]
        	});
    	}
    	console.log(this.inputValues);
    	document.body.className = "loaded";
	});
	  
	getVal(event) {
			console.log("start getVal");
			var item = event.item;
			var index = this.inputValues.indexOf(item);
			//this.inputs[index].value = event.srcElement.previousElementSibling.value;
			this.inputValues[index].value = event.srcElement.previousElementSibling.value;
			var obj={};
			obj[this.inputValues[index]["title"]] = {
					"min": this.inputValues[index]["min"],
					"max": this.inputValues[index]["max"],
					"value": this.inputValues[index]["value"]
			}
			/*console.log("obj");
			console.log(obj);
			console.log("Changing value: " + this.inputValues[index]["title"] + " to " + this.inputValues[index]["value"]);
			console.log("Before sent: ");
			console.log(this.values.server);*/
			this.sendChange(obj);
			/*console.log("Change sent. Server values: ");
			console.log(this.values.server);*/
			//event.srcElement.style.display= "none";
	  }
	  


  </script>
</inputs>