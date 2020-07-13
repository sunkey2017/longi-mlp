package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.longi.mlp.core.bean.ResultBean;
import com.longi.mlp.core.exception.BaseException;
import com.longi.mlp.core.exception.ErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * ${table.comment!} 控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Slf4j
@Api(value = "${package.ModuleName}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    private final ${table.serviceName} ${entity?uncap_first}Service;

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${entity?uncap_first}Service) {
        this.${entity?uncap_first}Service = ${entity?uncap_first}Service;
    }

    @ApiOperation(value = "创建${entity}对象")
    @PostMapping(value = "/")
    public ResultBean<${entity}> create(@RequestBody ${entity} ${entity?uncap_first}) {
        this.${entity?uncap_first}Service.save(${entity?uncap_first});
        return ResultBean.success(${entity?uncap_first});
    }

    @ApiOperation(value = "根据ID获取${entity}对象")
    @GetMapping(value = "/${r'{id}'}")
    public ResultBean<${entity}> get(@PathVariable(value = "id") String id) {
        ${entity} ${entity?uncap_first} = this.${entity?uncap_first}Service.getById(id);
        return ResultBean.success(${entity?uncap_first});
    }

    @ApiOperation(value = "删除${entity}对象")
    @DeleteMapping(value = "/${r'{id}'}")
    public ResultBean<${entity}> delete(@PathVariable(value = "id") String id) {
        int count = this.${entity?uncap_first}Service.removeById(id) ? 1 : 0;
        if (count != 1) {
            throw new BaseException(ErrorType.RES_NOT_FOUND, "删除${entity}对象失败, id = "+id);
        }
        return ResultBean.success();
    }

    @ApiOperation(value = "修改${entity}对象")
    @DeleteMapping(value = "/")
    public ResultBean<${entity}> update(@RequestBody ${entity} ${entity?uncap_first}) {
        int count = this.${entity?uncap_first}Service.updateById(${entity?uncap_first}) ? 1 : 0;
        if (count != 1) {
            throw new BaseException(ErrorType.RES_NOT_FOUND, "修改${entity}对象失败");
        }
        return ResultBean.success();
    }
}
</#if>
