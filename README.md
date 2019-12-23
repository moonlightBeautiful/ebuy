# EBuy 商城系统
##需求分析
就是在线购买系统，和淘宝类似。前台购物页面+后台管理。
使用技术：
    ssh+jquery+easyUI+tomcat+mysql+maven+idea
##环境框架搭建
mavan项目
    1.依赖jar包引入
    2.web.xml配置
        添加spring支持、添加Struts支持、添加session延迟加载到页面支持、添加自定义监听器。
    3.spring配置文件
        数据源（db_ebuy）、session工厂（指定用hibernate和指定持久层框架的主配置文件和自动扫描模型类）、 
        事务管理器（用hibernate的）、事务通知、事务切面、组件扫描
    4.Struts配置文件
        i18n，这个还不懂
        action扩展，这个还不懂
    5.hibernate配置文件
        方言、控制展示sql语句、自动更新表
    6.分层建包
        action、dao、entity、service、util
    7.基本类
        PageBean：分页类
        BaseDAO<T>、BaseDAOImpl<T>：数据库查询类
    学习：
        1.web.xml标签
            1. 启动一个WEB项目的时候,容器(如:Tomcat)会去读它的配置文件web.xml.读两个节点: <listener></listener> 和 <context-param></context-param>等
            2.紧接着,容器创建一个ServletContext(上下文),这个WEB项目所有部分都将共享这个上下文.
            3.容器将<context-param></context-param>转化为键值对,并交给ServletContext.
            4.容器创建<listener></listener>中的类实例,即创建监听.
            5.在监听中会有contextInitialized(ServletContextEvent args)初始化方法,在这个方法中获得ServletContext = ServletContextEvent.getServletContext();
                context-param的值 = ServletContext.getInitParameter("context-param的键");
        2.sessionFactory.openSession和sessionFactory.getCurrentSession
            区别：
                1.getCurrentSession则会先从上下文环境中找，找到了则会直接拿过来，如果没有，则创建新的session，且事务提交后会自动close。
                2.openSession每次打开的都是新的session，且需要close。
        3.createQuery与createSQLQuery两者区别是：
            1.前者用的hql语句进行查询，后者可以用sql语句查询
            2.前者返回类型问list<Bean>，后者返回list<Object>
        4.Query 
            query.setFirstResult(),query.setMaxResults();可以取到一定范围内的数据
        5.String replaceFirst(String regex,String replacement)
            使用给定的参数 replacement 替换字符串第一个匹配给定的正则表达式的子字符串。
        6.@Repository("")
            用在dao层上    
##建模
需求后建模
    1.需求-业务流程
        用户在线购买商品，商城展示新闻、评论、商品。
    2.建模-
        业务实体12个：
            1.用户(User t_user)、2.新闻(News t_news)、3.公告、4.评论、5.商品、6.商品大类、7.商品小类、8.标签、9.订单、10.购物车、11.购物车条目
            12.商品订单关联
        业务关系：尽量都弄成双向
            1.用户和订单：一对多，note：更新订单的时候，不能更新用户。
            2.商品和商品大类 多对一
            3.商品和商品小类 多对一 
            4.商品小类和商品大类 多对一 
            5.商品和订单 多对多???    
        插入基础数据  
    学习：
        1.hibernate模型（model）上标签的学习:javax.persistence.*
            0.表标签学习：
                @Entity  表明这个类是实体类，可以从数据库找到，
                @Table(name="t_notice")  ：帮你创建一个表，每次启动程序，会自动映射类和表，如果类新添加了属性，则在映射的时候会自动添加字段建立映射。
            1.主键标签学习：javax.persistence.*
                @Id
                @GeneratedValue(generator="_native")
                @GenericGenerator(name="_native",strategy="native")   //native：Hibernate根据底层数据库自行判断采用identity、hilo、sequence其中一种作为主键生成方式。
            2.普通类型字段配置：如果不做任何配置，会默认映射。
                @Column(length=20)   放在getter方法上，配置字段，比如长度，字段名称（不指定字段名。则默认和属性名一样）
                @Transient  忽略映射，虚拟属性。
            3.大数据字段clob
                @Lob
              	@Column(columnDefinition="TEXT")
            4.一对多关系
                @OneToMany(targetEntity = 多端实体Order.class, cascade = 级联操作CascadeType.AL, mappedBy = "交给对方维护主外键关系" ,fetch = FetchType.EAGER（加载关联对象的方式，FetchType.EAGER立即加载）)
                @JoinColumn(name = "外键名")
                说明
                    mappedBy：交给对方维护主外键关系，两方都在session且建立了关系，保存一方的时，是否在数据库中建立主外键关系。默认建立。
                    fetch： = FetchType.EAGER  ：立即加载多的一端。CascadeType.PERSIST????
                    也可以单独配置
                        @Cascade(value = {CascadeType.SAVE_UPDATE}) 
            5.对多一关系
                @ManyToOne(argetEntity = 一端实体User.class)， 也可以配置级联操作cascade，加载关联对象的方式fetch，是否交给对方维护主外键mappedBy（一般多的一方维护）
                @JoinColumn(name = "外键名", updatable = false)  //updatable，指定该列是否包含在session.update()时更新。默认值: true
        2.数据类型映射学习
            int--》int
            String--》varchar(默认)，可以配置成大数据类型，
                 @Lob
                 @Column(columnDefinition="TEXT")
                    --》text
            Date--datetime
            float--》
##缓存实现
常用数据缓存：就是在项目启动的时候把数据放到了application中
    1.在web.xml中配置自己的监听器
        <listener>  
            <listener-class>com.java1234.action.InitAction</listener-class>  
        </listener> 
    2.写一个IniAction，实现接口ServletContextListener,ApplicationContextAware
        ApplicationContextAware接口：
                如果Spring配置文件中所定义或者注解自动注入的Bean类实现了ApplicationContextAware 接口，那么在加载Spring配置文件时，
            会自动调用ApplicationContextAware 接口中的方法：setApplicationContext (ApplicationContext context)。
        ServletContextListener接口：
                ServletContextListener接口用于tomcat启动和关闭时自动加载函数contextInitialized(ServletContextEvent sce)和contextDestroyed(ServletContextEvent sce)         
        概念学习：    
            ServletContext:
                 servlet用来与容器间进行交互的接口的组合，在一个应用中(一个JVM)只有一个ServletContext, 换句话说，容器中所有的servlet都共享同一个ServletContext.
            ServletConfig: 
                 它与ServletContext的区别在于，servletConfig是针对servlet而言的，每个servlet都有它独有的serveltConfig信息，相互之间不共享.
            ApplicationContext: 
                 整个程序运行期间所需要的上下文信息。这个类是Spring实现容器功能的核心接口，它也是Spring实现IoC功能中最重要的接口。
                 注意这里的应用程序并不一定是web程序，也可能是其它类型的应用. 在Spring中允许存在多个applicationContext，这些context相互之间还形成了父与子，继承与被继承的关系，这也是通常我们所说的，
            WebApplicationContext: 
                其实这个接口不过是applicationContext接口的一个子接口罢了，只不过说它的应用形式是web罢了. 
                它在ApplicationContext的基础上，添加了对ServletContext的引用，即getServletContext方法   
    3.IniAction调用服务类缓存数据：
        商品大类信息加入到 application缓存中
        商品小类信息加入到 application缓存中
            因为获取商品大类的时候，立即获取了商品小类，所以不用再次获取放入到application缓存中
            @OneToMany(mappedBy = "bigType", fetch = FetchType.EAGER)
        标签信息加入到 application缓存中
        公告信息前8条加入到 application缓存中
        新闻信息前8条加入到 application缓存中
        热卖信息也就是商品,加入到 application缓存中
    学习到的：
        1.Spring注解@Component、@Repository、@Service、@Controller区别
            @Service服务层组件，用于标注业务层组件,表示定义一个bean，自动根据bean的类名实例化一个首写字母为小写的bean，                        
            @Controller用于标注控制层组件(如struts中的action)             
            @Repository持久层组件，用于标注数据访问组件，即DAO组件            
            @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。    
            @Entity  明这个class是实体类，可以从数据库找到，如果没有它会帮你创建一个数据表的作用
        2.@Autowired与@Resource
            简介：
               都是注入
            区别：
               @Autowired：会先按类型注入，然后按照名称注入，都无法找到唯一的一个实现类则报错。
               @Resource：会先按名称注入，然后按照类型注入，都无法找到唯一的一个实现类则报错。
            使用：
               可以用在字段或者setter方法上，但是不推荐用在字段上，容易发生空指针错误，推荐使用在setter方法上。 
        3. ApplicationContextAware接口：
                  如果Spring配置文件中所定义或者注解自动注入的Bean类实现了ApplicationContextAware 接口，那么在加载Spring配置文件时，
              会自动调用ApplicationContextAware 接口中的方法：setApplicationContext (ApplicationContext context)。
        4. ServletContextListener接口：
              ServletContextListener接口用于tomcat启动和关闭时自动加载函数contextInitialized(ServletContextEvent sce)和contextDestroyed(ServletContextEvent sce)         
        5.概念学习：    
            ServletContext:
                servlet用来与容器间进行交互的接口的组合，在一个应用中(一个JVM)只有一个ServletContext, 换句话说，容器中所有的servlet都共享同一个ServletContext.
            ServletConfig: 
                它与ServletContext的区别在于，servletConfig是针对servlet而言的，每个servlet都有它独有的serveltConfig信息，相互之间不共享.
            ApplicationContext: 
                整个程序运行期间所需要的上下文信息。这个类是Spring实现容器功能的核心接口，它也是Spring实现IoC功能中最重要的接口。
                注意这里的应用程序并不一定是web程序，也可能是其它类型的应用. 在Spring中允许存在多个applicationContext，这些context相互之间还形成了父与子，继承与被继承的关系，这也是通常我们所说的，
            WebApplicationContext: 
                其实这个接口不过是applicationContext接口的一个子接口罢了，只不过说它的应用形式是web罢了. 
                它在ApplicationContext的基础上，添加了对ServletContext的引用，即getServletContext方法         
##页面实现：
    1.下载易买网页面模板，放到webapp
        js+css+images+common+index.jsp
    2.top.jsp
        头部top.jsp：导航，首页的tag
        左侧left.jsp：商品分类（大类+小类菜单）,最近浏览
        中间index.jsp：特价，公告，新闻，热卖
         
         
         

        