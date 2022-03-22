(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 06/01/2022
 *)
program uniqueOrderTestWithError001;
type
  range=1..100;
var
  x, y:range;
  y,z,range: real;{y,z duplicate}
const
  xmin =-xmax; {xmax is undeclared, (after xmin) xmin won't be defined}
  xmax = 100;
type
  yrange = xrange;
  xrange = xmin..xmax;
begin
end.