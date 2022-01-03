(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest;
{type
  cardsuit = (clubs, diamonds, hearts, spades);}
var
  {card : cardsuit;}
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
  boolVar: Boolean;
  clubs2:Integer; {duplicate identifier is not allowed}
begin
  {card:= clubs;}
  {cardsuit2:= clubs2;}
  cardsuit2 := clubs2;
  cardsuit2 := clubs3; {Not a valid id in acceptable enum value map}
  boolVar := clubs2 < diamonds2;
  boolVar := clubs2 < clubs; {Not allowed, enum kind is not the same}

end.