package com.plugin.api.thread;
 
public class ThreadGroupDemo { 
	
    public static void main(String[] args){

        
        ThreadGroup threadGroup1 = new ThreadGroup("group1"){   
            public void uncaughtException(Thread t,Throwable e)   {   
                System.out.println(t.getName() + ":" + e.getMessage());   
             }   
        };   
        
        Thread[] list = new Thread[3];
  
        list[0] = new Thread(threadGroup1,new Runnable(){   
           public void run(){   
                throw new RuntimeException("运行异常！");   
           }   
        }); 
        
        list[1] = new Thread(threadGroup1,new Runnable(){   
            public void run(){   
                 System.out.println("============2");  
            }   
         });
        
        list[2] = new Thread(threadGroup1,new Runnable(){   
            public void run(){   
                 System.out.println("============3");  
            }   
        });
        
        for (int i = 0; i < list.length; i++) {
            list[i].start();
        }

       //System.out.println(threadGroup1.activeCount());
   
    }   
}
