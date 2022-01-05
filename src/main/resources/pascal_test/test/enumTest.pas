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
  weatherVar:weather;
  boolVar: Boolean;
  clubs2:Integer; {duplicate identifier is not allowed}
begin
  card := clubs;
  {cardsuit2:= clubs2;}
  cardsuit2 := clubs2;
  cardsuit2 := clubs3; {Not a valid id in acceptable enum value map}

  c :=1; {c is undeclared}
  cardsuit := 1; {Illegal assignment [cardsuit:=1]. Assigning value to identifier is not allowed}
  clubs2 := 1; {Illegal assignment [clubs2:=1]. Assigning value to identifieris not allowed}

  boolVar := clubs2 < diamonds2;
  boolVar := clubs < diamonds;
  boolVar := true = (diamonds2 < diamonds); {Expression [diamonds2<diamonds] types are incompatible}

  boolVar := clubs2 < clubs; {Not allowed, enum kind is not the same}
  boolVar := clubs < sunny; {Not allowed, enum kind is not the same}

end.