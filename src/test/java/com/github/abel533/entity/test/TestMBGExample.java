/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.abel533.entity.test;

import com.github.abel533.entity.EntityMapper;
import com.github.abel533.entity.mapper.CommonMapper;
import com.github.abel533.entity.model.Country;
import com.github.abel533.entity.model.CountryExample;
import com.github.abel533.mapper.MybatisHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试MBG生成的Example对象
 *
 * @author liuzh
 */
public class TestMBGExample {
    @Test
    public void testCountByExample() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
            EntityMapper entityMapper = new EntityMapper(commonMapper);

            CountryExample example = new CountryExample();
            example.createCriteria().andIdGreaterThan(100).andIdLessThanOrEqualTo(150);

            int count = entityMapper.countByExample(Country.class, example);
            Assert.assertEquals(50, count);

            example = new CountryExample();
            example.createCriteria().andCountrynameLike("A%");

            count = entityMapper.countByExample(Country.class, example);
            Assert.assertEquals(12, count);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByExample() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
            EntityMapper entityMapper = new EntityMapper(commonMapper);

            CountryExample example = new CountryExample();
            example.createCriteria().andIdGreaterThan(100).andIdLessThanOrEqualTo(150);

            int count = entityMapper.deleteByExample(Country.class, example);
            Assert.assertEquals(50, count);

            example = new CountryExample();
            example.createCriteria().andCountrynameLike("A%");

            count = entityMapper.deleteByExample(Country.class, example);
            Assert.assertEquals(12, count);
        } finally {
            //回滚
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByExample() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
            EntityMapper entityMapper = new EntityMapper(commonMapper);

            CountryExample example = new CountryExample();
            example.createCriteria().andCountrynameEqualTo("China");

            List<Country> countries = entityMapper.selectByExample(Country.class, example);
            Assert.assertEquals(1, countries.size());
            Assert.assertEquals("CN", countries.get(0).getCountrycode());

            example = new CountryExample();
            example.createCriteria().andIdEqualTo(100);

            countries = entityMapper.selectByExample(Country.class, example);
            Assert.assertEquals(1, countries.size());
            Assert.assertEquals("MY", countries.get(0).getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExampleSelective() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
            EntityMapper entityMapper = new EntityMapper(commonMapper);

            CountryExample example = new CountryExample();
            example.createCriteria().andCountrynameEqualTo("China");

            Country country = new Country();
            country.setCountryname("天朝");
            int count = entityMapper.updateByExampleSelective(country, example);

            Assert.assertEquals(1, count);

            example = new CountryExample();
            example.createCriteria().andCountrynameEqualTo("天朝");

            List<Country> countries = entityMapper.selectByExample(Country.class, example);
            Assert.assertEquals(1, countries.size());
            Assert.assertEquals("天朝", countries.get(0).getCountryname());
        } finally {
            //回滚
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExample() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
            EntityMapper entityMapper = new EntityMapper(commonMapper);

            CountryExample example = new CountryExample();
            example.createCriteria().andCountrynameLike("A%");

            Country country = new Country();
            country.setCountryname("统一");
            int count = entityMapper.updateByExample(country, example);

            Assert.assertEquals(12, count);

            example = new CountryExample();
            example.createCriteria().andCountrynameEqualTo("统一");

            List<Country> countries = entityMapper.selectByExample(Country.class, example);
            Assert.assertEquals(12, countries.size());
            Assert.assertEquals("统一", countries.get(0).getCountryname());
        } finally {
            //回滚
            sqlSession.rollback();
            sqlSession.close();
        }
    }

}
