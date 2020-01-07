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
        数据源（指定驱动、数据库db_ebuy）、session工厂（指定用hibernate工厂、数据源、指定持久层框架的主配置文件、自动扫描模型类的路径【自动生成表】）、 
        事务管理器（指定session工厂）、事务通知（指定事务换管理器、事务传播属性）、事务切面（切点、事务通知）
        组件扫描（@Controller、@Service、@Repository、@Entity、@Component）
    4.Struts配置文件
        struts.i18n.encoding：相当于HttpServletRequest的setCharacterEncoding()方法
        struts.action.extension：指定struts处理请求的.后缀名，也就是说指定的请求.后缀的我们才交给struts2控制器来处理，不是action扩展的我们不管（如图片、JS、CSS文件等）
    5.hibernate配置文件
        方言、控制台展示sql语句、自动更新表
    6.分层建包
        action、dao、entity、service、util
    7.基本类
        PageBean：分页类，当前页 每页大小 起始记录序号，note：起始记录序号只有get方法，自己计算出来的，(当前页-1)*每页大小
        BaseDAO<T>、BaseDAOImpl<T>：数据库查询类
    学习：
        1.web.xml标签
            1.启动一个WEB项目的时候,容器(如:Tomcat)会去读它的配置文件web.xml和创建一个ServletContext(上下文)，这个WEB项目所有的servlet都将共享这个上下文.
            2.<context-param></context-param>：容器会将<context-param></context-param>转化为键值对,并交给ServletContext。
            3.<listener></listener>：容器创建<listener></listener>中的类实例,即创建监听。
                在监听中会有contextInitialized(ServletContextEvent args)初始化方法,
                在这个方法中获得ServletContext = ServletContextEvent.getServletContext();
                context-param的值 = ServletContext.getInitParameter("context-param的键");
        2.组件扫描，Spring注解@Component、@Controller、@Service、@Repository区别
            @Controller控制层组件(如struts中的action)         
            @Service服务层组件，用于标注业务层组件,表示定义一个bean，自动根据bean的类名实例化一个首写字母为小写的bean，                                
            @Repository持久层组件，用于标注数据访问组件，即DAO组件
            @Entity模型层组件，如果从数据库没有找到，它会帮你创建一个数据表的作用            
            @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。 
        3.dao层，使用泛型，hibernate支持泛型
            Dao<T>                   
        3.sessionFactory.openSession和sessionFactory.getCurrentSession
            区别：
                1.getCurrentSession则会先从上下文环境中找，找到了则会直接拿过来，如果没有，则创建新的session，且事务提交后会自动close。
                2.openSession每次打开的都是新的session，且需要手动close。
        4.createQuery与createSQLQuery两者区别是：
            1.前者用的hql语句进行查询，后者可以用sql语句查询
            2.前者list返回类型问list<Bean>，后者返回list<Object>
        5.Query 
            query.setFirstResult(),query.setMaxResults();可以取到一定范围内的数据
        6.String replaceFirst(String regex,String replacement)
            使用给定的参数 replacement 替换字符串第一个匹配给定的正则表达式的子字符串。
        7.@Autowired与@Resource
          简介：
             都是注入
          区别：
             @Autowired：会先按类型注入，然后按照名称注入，都无法找到唯一的一个实现类则报错。
             @Resource：会先按名称注入，然后按照类型注入，都无法找到唯一的一个实现类则报错。
          使用：
             可以用在字段或者setter方法上，但是不推荐用在字段上，容易发生空指针错误，推荐使用在setter方法上。   
##建模
根据需求建模-数据库
    1.建模-数据库
        业务实体12个：10个实体对应数据库的表，2个实体不需要对应？？？
            1.用户(User-t_user)
            2.新闻(News-t_news)
            3.公告(Notice-t_notice)
            4.评论(Comment-t_comment)
            5.商品(product-t_product)
            6.商品大类(ProductBigType-t_bigType)
            7.商品小类(ProductSmallType-t_smallType)
            8.标签(Tag-t_tag)
            9.订单(Order-t_order)
            10.订单商品关联(OrderProduct-t_order_product)
            11.购物车(ShoppingCart)
            12.购物车条目(ShoppingCartItem)
        业务关系：尽量都弄成双向
            1.用户和订单：一对多，note：更新订单的时候，不能更新用户。
            2.商品和商品大类 多对一
            3.商品和商品小类 多对一 
            4.商品小类和商品大类 多对一 
            5.商品和订单 多对多???    
        插入基础数据  
    学习：
        1.hibernate模型（model）上标签的学习:javax.persistence.*：
            1.类标签：
                @Entity ：表明这个类是实体类，可以从数据库找到，
                @Table(name="t_notice")  ：帮你创建一个表，每次启动程序，会自动映射类和表，如果类新添加了属性，则在映射的时候会自动添加字段建立映射。
            2.主键标签：
                @Id：标识这个属性是主键
                @GeneratedValue(generator="_native")
                @GenericGenerator(name="_native",strategy="native")   //native：Hibernate根据底层数据库自行判断采用identity、hilo、sequence其中一种作为主键生成方式。
            3.非主键标签：放在getter方法上，
                1.普通类型字段配置：
                    如果不做任何配置，会使用默认配置映射。默认：
                        字段名-》和属性名一样
                        String对应varchar，长度为255
                    @Column(length=20) :放在getter方法上，配置字段，比如长度，字段名称（不指定字段名。则默认和属性名一样）
                    @Transient  忽略映射，不在表中创建字段，虚拟属性。
                    @Lob+@Column(columnDefinition="TEXT")：大数据字段clob
            4.关系标签：放在getter方法上，
                一对多：
                    @OneToMany(targetEntity = 多端实体.class, cascade = 级联操作CascadeType.AL, mappedBy = "交给对方维护主外键关系" ,fetch = FetchType.EAGER（加载关联对象的方式，FetchType.EAGER立即加载）)
                    @JoinColumn(name = "外键名") 
                多对一：
                    @ManyToOne(argetEntity = 一端实体.class)， 也可以配置级联操作cascade，加载关联对象的方式fetch，是否交给对方维护主外键mappedBy（一般多的一方维护）
                    @JoinColumn(name = "外键名", updatable = false)  //updatable，指定该列（外键）是否包含在session.update()时更新。默认值: true
                说明
                    argetEntity：另一方的实体类
                    cascade：级联操作方式，默认不级联操作
                    mappedBy：交给对方维护主外键关系，两方都在session且建立了关系，保存一方的时，是否在数据库中建立主外键关系。默认建立。
                        其值为另一端的本端类类型属性名
                    fetch：加载另一端的方式， = FetchType.EAGER  ：立即加载多的一端。CascadeType.PERSIST????
                    也可以单独配置
                        @Cascade(value = {CascadeType.SAVE_UPDATE}) 
                    @JoinColumn是配置外键的
        2.数据类型映射，java的数据类型-》数据库的数据类型
            int--》int
            String--》varchar(默认)，可以配置成大数据类型，
                 @Lob
                 @Column(columnDefinition="TEXT")
                    --》text
            Date--datetime
            float--》
##缓存实现
常用数据缓存：就是在项目启动的时候把首页数据放到了application中
    1.写一个IniAction，随着服务器启动而启动
        1.由spring来管理，使用注解@componet
        2.web.xml配置监听
            <listener>  
                <listener-class>com.ims.action.InitAction</listener-class>  
            </listener> 
        3.实现接口ServletContextListener
            tomcat启动和关闭时会自动加载函数contextInitialized(ServletContextEvent sce)和contextDestroyed(ServletContextEvent sce)   
        4.实现接口ApplicationContextAware
            加载Spring配置文件时会自动加载函数setApplicationContext (ApplicationContext context)
            组件扫描，不能确保Service和IniAction谁先被扫描到，谁先被加载，
            也就是说在tomcat启动时自动加载函数contextInitialized，执行getBean("服务bean"")时可能会得到null。
            所以，使用static ApplicationContext applicationContext 类属性   
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
        ServletContext:
            servlet用来与容器(tomcat)间进行交互的接口的组合，在一个应用中(一个JVM)只有一个ServletContext, 换句话说，容器中所有的servlet都共享同一个ServletContext.
        ServletConfig: 
            它与ServletContext的区别在于，servletConfig是针对servlet而言的，每个servlet都有它独有的serveltConfig信息，相互之间不共享.
        ApplicationContext: 
            整个程序运行期间所需要的上下文信息。这个类是Spring实现容器功能的核心接口，它也是Spring实现IoC功能中最重要的接口。
            注意这里的应用程序并不一定是web程序，也可能是其它类型的应用. 在Spring中允许存在多个applicationContext，这些context相互之间还形成了父与子，继承与被继承的关系，这也是通常我们所说的，
        WebApplicationContext: 
            其实这个接口不过是applicationContext接口的一个子接口罢了，只不过说它的应用形式是web罢了. 
            它在ApplicationContext的基础上，添加了对ServletContext的引用，即getServletContext方法         
##页面实现：
基本主页搭建
    1.下载易买网页面模板，放到webapp
        js+css+images+common+index.jsp
    2.top.jsp
        头部top.jsp：导航，首页的tag
        左侧left.jsp：商品分类（大类+小类菜单）,最近浏览
        中间index.jsp：特价，公告，新闻，热卖
细节实现：
    3.大类商品信息列表显示
        点击上边分类导航条上的商品大类，分页显示对应类型的商品
            1.页面传参
            2.ProductAction接受参数，传递给ProductService
            3.ProductService拼接hql，查询大类商品
            4.productList.jsp 展示查询到的商品和分页信息
    4.小类商品信息列表显示
        点击左边分类导航条上的商品小类，分页显示对应类型的商品
            1.页面传参
            2.ProductAction接受参数，传递给ProductService
            3.ProductService拼接hql，查询小类小品
    5.商品搜索
        右上角输入值，点击搜索商品
    6.商品详情页面
    7.最近浏览
学习到的：
    1.ServletRequestAware接口
        action类实现这个接口，可以自动注入request
    2.html中 大鱼号 和 小于号
        <   &lt;
        >   &gt;
    3.struts2
        action如果找不到对应的方法，则执行默认的方法
    4.service层的查询，传入的参数是3个
        条件（要查询的类），分页（PageBean）	 
    5.action层：使用自动set接受参数
    6.页面传参：
        <a href="action?参数"><a/>
    7.tomcat默认url携带参数编码是ios-8859-1，,中文会乱码
        解决：
            tomcat目录下的config目录下，server.xml中，Service Connector修改URIEncoding="UTF-8，如果没有则添加。
        验证：
            idea中不知道，eclipse中生成的server服务器，可以查看server.xml
        
              
        
         
         
         

        