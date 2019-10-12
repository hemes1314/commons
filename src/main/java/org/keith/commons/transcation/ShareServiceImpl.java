package org.keith.commons.transcation;

/*
@Service
public class ShareServiceImpl {

    org.slf4j.Logger logger = getLogger(this.getClass());

    @Autowired
    TestServiceImpl testService;
    @Autowired
    LogMapper logMapper;

    public void execTest() {
        Application application=new Application();
        try{
            application.setName("test");
            applicationMapper.insert(application);
        }catch (Exception e){

        }
        application.setName("test2");
        application.setId(UUID.randomUUID().toString());
        application.setSecretCode("1234567");
        applicationMapper.insert(application);
    }
    public void execTest2() {
        Application application=new Application();
        try{
            application.setName("test");
            applicationMapper.insert(application);
        }catch (Exception e){

        }
        Log log=new Log();
        log.setId(UUID.randomUUID().toString());
        log.setDesc("test2");
        log.setCreatedTime(new Date());
        log.setLastUpdateTime(new Date());
        logMapper.insert(log);
    }

    public void execTest3() {
        Application application=new Application();
        try{
            application.setName("test");
            applicationMapper.insert(application);
        }catch (Exception e){

        }
        saveLog();
    }
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveLog(){
        Log log=new Log();
        log.setId(UUID.randomUUID().toString());
        log.setDesc("xxtest4");
        log.setCreatedTime(new Date());
        log.setLastUpdateTime(new Date());
        logMapper.insert(log);
    }
    public void execTest4() {
        Application application=new Application();
        try{
            application.setName("test4");
            applicationMapper.insert(application);
        }catch (Exception e){
            logger.error("error:"+e.getMessage());
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLog5(){
        Log log=new Log();
        log.setId(UUID.randomUUID().toString());
        log.setDesc("xxtest5");
        log.setCreatedTime(new Date());
        log.setLastUpdateTime(new Date());
        logMapper.insert(log);
    }
    public void execTest5() {
        Application application=new Application();
        try{
            application.setName("test5");
            applicationMapper.insert(application);
        }catch (Exception e){
            logger.error("error:"+e.getMessage());
        }
    }

    public void execTest6() {
        Application application=new Application();
        try{
            application.setName("test6");
            applicationMapper.insert(application);
        }catch (Exception e){
            logger.error("error:"+e.getMessage());
        }
        testService.saveLog6();
    }

    public void execTest7() {
        Application application=new Application();
        try{
            application.setName("test7");
            applicationMapper.insert(application);
        }catch (Exception e){
            logger.error("error:"+e.getMessage());
        }
        testService.saveLog7();
    }
}

 */
