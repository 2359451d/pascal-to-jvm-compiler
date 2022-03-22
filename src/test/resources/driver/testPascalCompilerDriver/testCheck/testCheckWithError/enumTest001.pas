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
begin
  card := clubs;
  card:= sunny; {incompatible enum kind}

end.