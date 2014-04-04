
function createNode(){
  var root = {
    "id" : "JG0",
    "text" : "JGROOT",
    "value" : "#KJH#LJSDF#123",
    "showcheck" : true,
    complete : true,
    "isexpand" : true,
    "checkstate" : 0,
    "hasChildren" : true
  };
  var arr = [];
  root["ChildNodes"] = arr;
  return root; 
}

treedata = [createNode()];
