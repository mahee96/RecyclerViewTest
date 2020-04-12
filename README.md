# RecyclerView Implementation - Android

Sample RecyclerView implementation (Vertical Scroll List)

This RecyclerView has a custom RecyclerView.Adapter which is used to create the Views(that are displayed on screen), via ViewHolder
(custom RecyclerView.ViewHolder)

    custom ViewHolder creates the Child ViewGroup from xml Template(implicit Inflation) or an explicit inflation using LayoutInflator
    with java code.

Working:

    1. custom RecyclerView.Adapter Overrides mandatory methods such as onCreateViewHolder(), onBindViewHolder() and getItemCount().
    2. getItemCount() is crucial since it specifies the limit of data that will be used by the ViewHolders during recycling. 
       More on this while explaining onBindViewHolder() below.
    
    3. onCreateViewHolder() is used to create a View in the form of ViewHolder object that inturn contains the actual view
       (layout - viewGroup)
    4. Thus onCreateViewHolder() is invoked to create ViewHolderObjects as long as the screen has enough space to fill in 
       new ViewHolders
    5. Hence for any moment the screen will have constant number of ViewHolders that are visible on the screen(8 or more based on 
       screen size) and constant recyclable ViewHolders that are available offscreen in background(2 or 3) that are cached and put 
       into scroll direction of the list. (either appending to up or down the list).
    
    6. onBindViewHolder() is used to bind the created ViewHolders with appropriate data. The ViewHolder recycling is done internally 
       by RECYCLERVIEW and it passes the current holder that has been chosen to be recycled, to this method so that its data can be
       refreshed and the view can be reused with different data.
    7. since we have an instance of the viewHolder instead of directly manipulating the data of view by doing 
       "mViewHolder.mTextView.setText("blah blah");"
       we can delegate it to the helper methods in ViewHolder objects that can inturn process the viewUpdate request. 
       ex: "mViewHolder.bindDataToView();"    here bindDataToView() method must be implemented in ViewHolder class.
    8. The getItemCount specifies a HARD LIMIT on the number of times the onBindViewHolder() is called in any direction. 
       ex: if we created 8(on screen)+2(off screeen) views using onCreateViewHolder() and called onBindViewHolder() to show the data, 
       then we are no more allowed to create views using onCreateViewHolder() but are allowed to recycle views using onBindViewHolder() 
       for only getItemCount-(8+2) times. So if we have getItemCount() == 100 then remaining calls to onBindViewHolder() 
       is 100-8-2 = 90 times.
       This property is significant in implementation of the RECYCLERVIEW Class itself to limit over-recycling when we have reached end
       of source data either direction. 

Click Events:

    1. Since each ViewHolder item has a direct relationship to the actual ViewGroup whose child views we are interested in
       (ex: TextView in LinearLayout),
       we can implement the child view's(textView's) onClickListener interface which requires Overriding the onClick() (Handler) 
       method that will be invoked by the textView. Inorder to be invoked we first need to be SUBSCRIBED/REGISTERED into TextView's 
       click events which can be done by using tv.setOnclickListener(this). Here we use "this" because this ViewHolder class by itself 
       is now an instance of View.OnclickListener interface which is required by TextView class inside its setOnclickListener() 
       REGISTRATION method.
    2. Now that we know how the REGISTRATION/SUBSCRIPTION of EVENT HANDLERS in the EVENT GENERATOR class are performed using 
       EVENT LISTENER INTERFACES, we can use the same principle to delegate the event handling, to the UI class(MainActivity) that 
       are subscribed to our ViewHolderClass's (or RecyclerView.Adapter outer class's) Events.
    3. This can be seen by passing an ListItemClickListener interface instance to the RecyclerView.Adapter outerclass's constructor 
       which contains the ViewHolder inner class.
    4. Since the UI class (MainActivity) is the subscriber here for clickEvents of viewHolders in the RecyclerView, it should IMPLEMENT 
       the ListItemClickListener interface and also should OVERRIDE the ListItemOnClickHandler() method of the interface.
    5. The ViewHolder which has subscribed to the TextView child view's Click Events in its constructor using 
       tv.setOnClickListener(this), can delegate the click event of TextView passed to its onCLick() handler further down to the 
       UI class(MainActivity) which has registered/subscribed itself to ViewHolder's events indirectly via RecyclerView.Adapter 
       parent class. 
    6. This invoking the listItemOnClickHandler() on all the listeners(MainActivity's instance(as an interaface instance)) stored
       locally in RecyclerView.Adapter object. (Listener instances can be stored locally as an ArrayList) and a 'for' loop can be used
       to call the handlers of all the subscribed listeners. ex: mListItemClickListener.listItemOnClickHandler(some data) inside 
       ViewHolder's onClick() method.
