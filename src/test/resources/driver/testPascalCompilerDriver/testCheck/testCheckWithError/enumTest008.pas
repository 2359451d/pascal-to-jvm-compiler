(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest008;
type
  cardsuit = (clubs, diamonds, hearts, spades);
  weather = (sunny, rainy);
var
  boolVar: Boolean;
begin
  boolVar :=clubs < diamonds;
  boolVar := clubs < sunny; {Not allowed, enum kind is not the same}

end.
