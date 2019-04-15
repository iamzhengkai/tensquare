package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public void save(Label label) {
        label.setId("" + idWorker.nextId());
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public void updateById(Label label) {
        labelDao.save(label);
    }

    public List<Label> findByWhere(Label label) {
        return labelDao.findAll(createSpecification(label));
    }

    private Specification<Label> createSpecification(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(label.getState())){
                    Predicate predicate = criteriaBuilder.like(root.get("state").as(String.class), "%" + label.getState() + "%");
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(label.getRecommend())){
                    Predicate predicate = criteriaBuilder.like(root.get("recommend").as(String.class),"%" + label.getRecommend() + "%" );
                    predicates.add(predicate);
                }

                if (null != label.getCount()){
                    Predicate predicate = criteriaBuilder.equal(root.get("count").as(Long.class), label.getCount());
                    predicates.add(predicate);
                }
                if (null != label.getFans()){
                    Predicate predicate = criteriaBuilder.equal(root.get("fans").as(Long.class), label.getFans());
                    predicates.add(predicate);
                }

                Predicate[] predicateArray = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicateArray));
                /**
                    @link https://www.cnblogs.com/DreamDrive/p/4317263.html
                    public <T> T[] toArray(T[] a)方法会获取参数数组a的类型，并且针对每个元素进行类型转换
                    而toArray()直接返回的是一个Object[],不能直接将一个Object[]转换成指定类型的数组T[]

                    为什么Object[]数组不能强转成Integer[]数组呢?
                    其实Object[]数组和Integer[]数组之前的关系并没有继承之间的关系,Integer[]的是Object的子类,并不是Object[]数组的子类.
                    Object[]数组是Object的子类.....
                    强转的话还是要一个个的对单独的元素进行强转.
                 */
                //为什么会报错，不能进行强转？
                //[Ljava.lang.Object; cannot be cast to [Ljavax.persistence.criteria.Predicate;
                //return criteriaBuilder.and((Predicate[]) predicates.toArray());

                /*
                *   private String labelname;//标签名称
                    private String state;//状态
                    private Long count;//使用数量
                    private Long fans;//关注数
                    private String recommend;//是否推荐
                * */
            }
        };
    }

    public Page<Label> findByWhere(Label label, Integer page, Integer size) {
        Specification<Label> specification = createSpecification(label);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification,pageRequest);
    }
}
