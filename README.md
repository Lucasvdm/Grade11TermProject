# Grade11TermProject
Grade 11 Computer Science Intermediate Term Project

Allows user to draw, save, and compare signatures.

The user may use black, blue, red, or green to draw their signature, and there is an additional eraser option
for correcting any mistakes.  The "Open" button allows the user to open a saved signature for analysis and comparison;
if there is already a signature open, the open button will replace the drawing pad with a second saved signature
so that two pre-drawn signatures may be compared.  When a signature is open, there is a "Close" button to allow the user
to close it when they are done with the comparison.  The "Save" button saves the current contents of the scratchpad.
The "Clear" button returns the scratchpad to its empty state.  The "Compare" button analyzes and compares two open signatures
to determine if they are a match.

The comparison is by no means an official or standardized test.  It works sometimes and is very finicky.  The acceptable
margin of error between the two signatures can be edited in the code to increase or decrease how scrict the comparison is.

Please note that the mouse should be moved relatively slowly while drawing the signature in order to create a smooth, solid line for
aesthetics and ease of comparison.

I didn't use the NetBeans IDE GUI Builder for this one, I made the GUI by hardcoding it.  That was... not fun, to say the least.

A runnable .jar can be found in the dist.zip file.
