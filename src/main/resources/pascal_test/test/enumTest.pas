(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest;
type
  cardsuit = (clubs, diamonds, hearts, spades);
  weather = (sunny, rainy);
var
  card : cardsuit;
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
<<<<<<< HEAD
  weatherVar:weather;
=======
>>>>>>> c0239b4f72370cd8532299c7c1c491508217c9ff
  boolVar: Boolean;
  clubs2:Integer; {duplicate identifier is not allowed}
begin
  card := clubs;
  {cardsuit2:= clubs2;}
  cardsuit2 := clubs2;
  cardsuit2 := clubs3; {Not a valid id in acceptable enum value map}
<<<<<<< HEAD

  c :=1;
  cardsuit := 1; {Illegal assignment [cardsuit:=1]. Assigning value to identifier is not allowed}
  clubs2 := 1; {Illegal assignment [clubs2:=1]. Assigning value to identifieris not allowed}

  boolVar := clubs2 < diamonds2;
  boolVar := true = (diamonds2 < diamonds); {Expression [diamonds2<diamonds] types are incompatible}
  boolVar := clubs < diamonds;

  boolVar := clubs2 < clubs; {Not allowed, enum kind is not the same}
  boolVar := clubs < sunny; {Not allowed, enum kind is not the same}
=======
  boolVar := clubs2 < diamonds2;
  boolVar := clubs2 < clubs; {Not allowed, enum kind is not the same}
>>>>>>> c0239b4f72370cd8532299c7c1c491508217c9ff

end.