<outputs>
<div class="flex-container">
<h2>Outputs</h2>
<div each={ outputValues } class="flex-item" id="outputitem">
  <strong>{ title }</strong>: { value }
</div>
</div>
<script>
this.outputValues = [];
  this.on('update', function() {
  if(!this.values) return;
  this.outputValues = [];
    for(var value in this.values.client){
        console.log(value + " has " + this.values.client[value]);
        this.outputValues.push({
          "title": value,
          "value": this.values.client[value]
        });
    }
    console.log(this.outputValues);
  });
</script>
</outputs>
