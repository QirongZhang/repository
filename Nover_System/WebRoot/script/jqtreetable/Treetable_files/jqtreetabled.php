<?php
/* 
Copyright: Paul Hanlon

Released under the MIT/BSD licence which means you can do anything you want 
with it, as long as you keep this copyright notice 
*/
class jQTreeTable{
	public function init($map,$options,$tbodyid){
    $script = <<<EOT
(function(jq){
  jq.fn.jqTreeTable=function(map, options){
    var opts = jq.extend({openImg:"",shutImg:"",leafImg:"",lastOpenImg:"",lastShutImg:"",lastLeafImg:"",vertLineImg:"",blankImg:"",collapse:false,column:0,striped:false,highlight:false,state:true},options),
    mapa=[],mapb=[],tid=this.attr("id"),collarr=[],
	  stripe=function(){
      if(opts.striped){
  		  $("#"+tid+" tr:visible").filter(":even").addClass("even").end().filter(":odd").removeClass("even");
      }
	  },
    buildText = function(parno, preStr){//Recursively build up the text for the images that make it work
      var mp=mapa[parno], ro=0, pre="", pref, img;
      for (var y=0,yl=mp.length;y<yl;y++){
        ro = mp[y];
        if (mapa[ro]){//It's a parent as well. Build it's string and move on to it's children
          pre=(y==yl-1)? opts.blankImg: opts.vertLineImg;
          img=(y==yl-1)? opts.lastOpenImg: opts.openImg;
          mapb[ro-1] = preStr + '<img src="'+img+'" class="parimg" id="'+tid+ro+'">';
          pref = preStr + '<img src="'+pre+'" class="preimg">';
          arguments.callee(ro, pref);
        }else{//it's a child
          img = (y==yl-1)? opts.lastLeafImg: opts.leafImg;//It's the last child, It's child will have a blank field behind it
          mapb[ro-1] = preStr + '<img src="'+img+'" class="ttimage" id="'+tid+ro+'">';
        }
      }
    },
    expandKids = function(num, last){//Expands immediate children, and their uncollapsed children
      jq("#"+tid+num).attr("src", (last)? opts.lastOpenImg: opts.openImg);//
      for (var x=0, xl=mapa[num].length;x<xl;x++){
        var mnx = mapa[num][x];
        jq("#"+tid+mnx).parents("tr").removeClass("collapsed");
  			if (mapa[mnx] && opts.state && jq.inArray(mnx, collarr)<0){////If it is a parent and its number is not in the collapsed array
          arguments.callee(mnx,(x==xl-1));//Expand it. More intuitive way of displaying the tree
        }
      }
    },
    collapseKids = function(num, last){//Recursively collapses all children and their children and change icon
      jq("#"+tid+num).attr("src", (last)? opts.lastShutImg: opts.shutImg);
      for (var x=0, xl=mapa[num].length;x<xl;x++){
        var mnx = mapa[num][x];
        jq("#"+tid+mnx).parents("tr").addClass("collapsed");
        if (mapa[mnx]){//If it is a parent
          arguments.callee(mnx,(x==xl-1));
        }
      }
    },
  	creset = function(num, exp){//Resets the collapse array
  		var o = (exp)? collarr.splice(jq.inArray(num, collarr), 1): collarr.push(num);
      cset(tid,collarr);
  	},
  	cget = function(n){
	  	var v='',c=' '+document.cookie+';',s=c.indexOf(' '+n+'=');
	    if (s>=0) {
	    	s+=n.length+2;
	      v=(c.substring(s,c.indexOf(';',s))).split("|");
	    }
	    return v||0;
  	},
    cset = function (n,v) {
  		jq.unique(v);
	  	document.cookie = n+"="+v.join("|")+";";
	  };
    for (var x=0,xl=map.length; x<xl;x++){//From map of parents, get map of kids
      num = map[x];
      if (!mapa[num]){
        mapa[num]=[];
      }
      mapa[num].push(x+1);
    }
    buildText(0,"");
    jq("tr", this).each(function(i){//Inject the images into the column to make it work
      jq(this).children("td").eq(opts.column).prepend(mapb[i]);
      jq(this).children("td").eq(4).prepend("["+((mapa[i+1])? mapa[i+1]: "Child")+"]");//REMOVE THIS for production
    });
		collarr = cget(tid)||opts.collapse||collarr;
		if (collarr.length){
			cset(tid,collarr);
	    for (var y=0,yl=collarr.length;y<yl;y++){
	      collapseKids(collarr[y],($("#"+collarr[y]+ " .parimg").attr("src")==opts.lastOpenImg));
	    }
		}
    stripe();
    jq(".parimg", this).each(function(i){
      var jqt = jq(this),last;
      jqt.click(function(){
        var num = parseInt(jqt.attr("id").substr(tid.length));//Number of the row
        if (jqt.parents("tr").next().is(".collapsed")){//If the table row directly below is collapsed
          expandKids(num, (jqt.attr("src")==opts.lastShutImg));//Then expand all children not in collarr
					if(opts.state){creset(num,true);}//If state is set, store in cookie
        }else{//Collapse all and set image to opts.shutImg or opts.lastShutImg on parents
          collapseKids(num, (jqt.attr("src")==opts.lastOpenImg));
					if(opts.state){creset(num,false);}//If state is set, store in cookie
        }
        stripe();//Restripe the rows
      });
    });
    if (opts.highlight){//This is where it highlights the rows
      jq("tr", this).hover(
        function(){jq(this).addClass("over");},
        function(){jq(this).removeClass("over");}
      );
    };
  };
  return this;
})(jQuery);
$(function(){//Initialise the treetable
  var map1=[0, 0, 2, 3, 4, 4, 6, 4, 2, 9, 10, 0, 0, 13, 0, 0, 0, 17, 17, 0],
  map2=[$map];
  var options1 = {openImg: "../images/tv-collapsable.gif", shutImg: "../images/tv-expandable.gif", leafImg: "../images/tv-item.gif", lastOpenImg: "../images/tv-collapsable-last.gif", lastShutImg: "../images/tv-expandable-last.gif", lastLeafImg: "../images/tv-item-last.gif", vertLineImg: "../images/vertline.gif", blankImg: "../images/blank.gif", collapse: false, column: 1, striped: true, highlight: true, state:true};
  $("#treet1").jqTreeTable(map1, options1);
  $("#treet2").jqTreeTable(map2, {$options});
});
EOT;
    $style = <<<EOT
.collapsed { display: none; }
.tablemain {background-color:#ccf;border-collapse: collapse; border: solid 1px #447; padding: 0px; text-align: left; }
.tablemain td {margin-left:3px;}
#treet1 {background-color:#ffc}
#treet2 {background-color:#ffc}
#treet1 td { font: normal 10pt Arial; padding: 0px 2px 0px 0px; cursor: pointer;}
#treet2 td { font: normal 10pt Arial; padding: 0px 2px 0px 0px; cursor: pointer;}
.adeimg, .ttimage, .parimg, .preimg { border: none; margin: 0px; padding: 0px; vertical-align: bottom; width: 16px; height: 16px; }
.adeimg, .parimg {cursor: pointer; }
.even { background-color: #ddd; }
.over { background-color: #66f; }
EOT;
    return array($script,$style);
	}
}
    $demo1 = <<<EOT
<table class="tablemain"><thead><tr><th>Row No</th><th>Description</th><th>Path to Row</th><th>Level</th><th>Status</th></tr></thead>
<tbody id="treet1">
<tr><td>1</td><td>Child of 0</td><td>[0, 1]</td><td>1</td><td></td></tr>
<tr><td>2</td><td>Child of 0</td><td>[0, 2]&nbsp;</td><td>1</td><td></td></tr>
<tr><td>3</td><td>Child of 2</td><td>[0, 2, 3]&nbsp;</td><td>2</td><td></td></tr>
<tr><td>4</td><td>Child of 3</td><td>[0, 2, 3, 4]&nbsp;</td><td>3</td><td></td></tr>
<tr><td>5</td><td>Child of 4</td><td>[0, 2, 3, 4, 5]&nbsp;</td><td>4</td><td></td></tr>
<tr><td>6</td><td>Child of 4</td><td>[0, 2, 3, 4, 6]&nbsp;</td><td>4</td><td></td></tr>
<tr><td>7</td><td>Child of 6</td><td>[0, 2, 3, 4, 6, 7]&nbsp;</td><td>5</td><td></td></tr>
<tr><td>8</td><td>Child of 4</td><td>[0, 2, 3, 4, 8]&nbsp;</td><td>4</td><td></td></tr>
<tr><td>9</td><td>Child of 2</td><td>[0, 2, 9]&nbsp;</td><td>2</td><td></td></tr>
<tr><td>10</td><td>Child of 9</td><td>[0, 2, 9, 10]&nbsp;</td><td>3</td><td></td></tr>
<tr><td>11</td><td>Child of 10</td><td>[0, 2, 9, 10, 11]&nbsp;</td><td>4</td><td></td></tr>
<tr><td>12</td><td>Child of 0</td><td>[0, 12]&nbsp;</td><td>1</td><td></td></tr>
<tr><td>13</td><td>Child of 0</td><td>[0, 13]&nbsp;</td><td>1</td><td></td></tr>
<tr><td>14</td><td>Child of 13</td><td>[0, 13, 14]&nbsp;</td><td>2</td><td></td></tr>
<tr><td>15</td><td>Child of 0</td><td>[0, 15]&nbsp;</td><td>1</td><td></td></tr>
<tr><td>16</td><td>Child of 0</td><td>[0, 16]&nbsp;</td><td>1</td><td></td></tr>
<tr><td>17</td><td>Child of 0</td><td>[0, 17]&nbsp;</td><td>1</td><td></td></tr>

<tr><td>18</td><td>Child of 17</td><td>[0, 17, 18]&nbsp;</td><td>2</td><td></td></tr>
<tr><td>19</td><td>Child of 17</td><td>[0, 17, 19]&nbsp;</td><td>2</td><td></td></tr>
<tr><td>20</td><td>Child of 0</td><td>[0, 20]&nbsp;</td><td>1</td><td></td></tr>
</tbody></table>
EOT;
$options = '{openImg: "../images/fopen.gif", shutImg: "../images/fshut.gif", leafImg: "../images/new.gif", lastOpenImg: "../images/fopen.gif", lastShutImg: "../images/fshut.gif", lastLeafImg: "../images/new.gif", vertLineImg: "../images/blank.gif", blankImg: "../images/blank.gif", collapse: false, column: 1, striped: true, highlight: true, state:false}';
$tbodyid = "treet2";
list($str2,$map2) = makeDemo(20,$tbodyid);
$body = $demo1."<br />\n".$str2;
$jq = new jQTreeTable();
$vars = $jq->init($map2,$options,$tbodyid);
  echo <<<EOT
<html>
  <head>
    <title>JQTreeTable</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
    <script type="text/javascript">
{$vars[0]}
    </script>
    <style type="text/css">
{$vars[1]}
    </style>
  </head>
  <body>
$body
  </body>
</html>
EOT;
	function makeDemo($num,$id){
    $head = array("Row No", "Description", "Path to Row", "Level", "Status");
    $parents = array(0,1);//Holds the row number of the available parents
    $jsmap = array(0);//Holds the eventual map sent to jqTreetable
    $parstr = "";
    $tabstr = '<tr><td>1</td><td><a href="jQTreeTable.zip" onclick="alert(\'Ive been clicked!\');">Click to download</a></td><td>[0, 1]</td><td>1</td><td></td></tr>'."\n";
    for ($i=0; $i<count($head); $i++){
    	$headrow .= '<th>'.$head[$i].'</th>';
    }
    $headrow ='<tr>'.$headrow.'</tr>';
    for ($x=1; $x<$num;$x++){
    	$cnt = count($parents)-1;
      $rand = rand(0, $cnt);//Rand is basically the level chosen as parent
      $par = $parents[$rand];
      array_push($jsmap, $par);
      if ($rand == $cnt){
      	array_push($parents, $x+1);
      }else{
      	$parents[$rand+1] = $x+1;
      	$parents = array_slice($parents, 0, $rand+2);
      }
      $parstr = "[".implode(", ", $parents)."]";
      $tabstr .= '<tr><td>'.($x+1).'</td><td>Child of '.$par.'</td><td>'.$parstr.'&nbsp;</td><td>'.($rand+1).'</td><td></td></tr>'."\n";
    }
		return array('<table class="tablemain"><thead>'.$headrow."</thead>\n<tfoot>".$headrow."</tfoot>\n".'<tbody id="'.$id.'">'.$tabstr."</tbody><tr><td>Random</td><td>data</td><td>outside</td><td>treetable</td><td>tbody</td></tr>\n</table>\n", implode(", ", $jsmap));
	}
?>