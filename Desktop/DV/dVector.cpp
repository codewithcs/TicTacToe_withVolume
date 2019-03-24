class dVector
{


 dVector ( char * file  , char * id  )  // topology.txt , H or some other router
  {

   fstream topology(file);

   string line; // current line of file
   string field; // current token (to be put into entry's field)

   char idName = id[0]; // name of id

   myId = indexOf(id[0]); // id[0]-'A'

   // initialize Details

   for (int dest = 0; dest < no_of_routers; dest++)
   {
   Details[dest].setNextRouterName('0');
   Details[dest].setNextRouterPort(-1);
   Details[dest].setCost(-1);
   Details[dest].setAlive();
   }

   while ( getline(topology, line) )
   { // parse file line by line
   stringstream linestream(line);

   dv_info entry; // struct dv_info

   entry.setAlive();

   // source router
   getline(linestream, field, ',');

   char name = field[0]; // source

   // destination router
   getline(linestream, field, ','); // get to the next string after ,

   int dest = indexOf(field[0]); // -'A'

   neighbour_info n;

   n.name = field[0]; // set the name of the neighbour

   entry.setNextRouterName(field[0]);

   // destination port number
   getline(linestream, field, ',');

   int port = atoi( field.c_str() ); // atoi converts string to integer


   entry.setNextRouterPort(port);

   n.portno = port;

   memset( (char *)&n.addr, 0, sizeof(n.addr) );

   n.addr.sin_family = AF_INET;
   n.addr.sin_addr.s_addr = inet_addr("127.0.0.1");
   n.addr.sin_port = htons(port);

   // link cost
   getline(linestream, field, ',');

   entry.setCost( atoi ( field.c_str() ) ) ;


   if ( idName == 'H' )
   {
   int i;

   for (i = 0; i < neighbours.size(); i++) //initially its size is 0
   {
   if ( neighbours[i].name == n.name )
   break;
   }

   if ( i == neighbours.size() )
   {
     neighbours.push_back(n) ;
   }

  }

   else if ( name == idName )
   {
   aliveTime(n);
   neighbours.push_back(n); // store neighbour
   Details[dest] = entry;
   }

   portnos[n.name] = n.portno;

   }

   // special port number for sending data packet
   portnos['H'] = 11111;

   memcpy( (void*)Details_backup, (void*)Details, sizeof(Details) );

   if ( nameOf(myId) != 'H' )
   {
     print( Details, nameOf( myId), "Initial routing table", true);
   }

  }

}
