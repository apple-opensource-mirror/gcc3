static char junk[] = "\n@(#) LIBU77 VERSION 19980709\n";

char __G77_LIBU77_VERSION__[] = "0.5.27 20020105 (experimental)";

#include <stdio.h>

void
g77__uvers__ ()
{
  fprintf (stderr, "__G77_LIBU77_VERSION__: %s", __G77_LIBU77_VERSION__);
  fputs (junk, stderr);
}