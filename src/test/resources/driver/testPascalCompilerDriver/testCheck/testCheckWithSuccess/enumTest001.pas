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
  boolVar: Boolean;
begin
  card := clubs;
  cardsuit2 := clubs2;


  boolVar := clubs2 < diamonds2;
  boolVar := clubs < diamonds;

end.