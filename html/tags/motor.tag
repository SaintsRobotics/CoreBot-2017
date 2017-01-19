
<motors>
<div class="flex-container">
<h2> motors </h2>
	<div  class="flex-item" id="motoritem" each={ motorValues }>
		<p class="wait">

		<form>
		<h3 id="motorH3">{ title }</h3>
			<input type="text" name="valueEdit1" id="textInput" placeholder="{ value }" oninput="rangeInput.value = textInput.value;submitter.style.display= 'initial'"></input>
			<input type="button" id="submitter" name="sub1" class="submitVal" value="submit" onclick={ parent.getVal }></input>
		</form>
		</p>
	</div>
	</div>

	 <script>
	this.motorValues = [];
	
this.on('update', function() {
  if(!this.values) return;
  this.motorValues = [];
    for(var value in this.values.server){
        this.motorValues.push({
          "title": value,
          "value": this.values.server[value]
        });
    }
  });
	  
	getVal(event) {
			var item = event.item;
			var index = this.motorValues.indexOf(item);
			this.motorValues[index].value = event.srcElement.previousElementSibling.value;
			var obj={};
			obj[this.motorValues[index]["title"]] = this.motorValues[index]["value"];
			this.sendChange(obj);
			//event.srcElement.style.display= "none";
	  }

  </script>
</motors>