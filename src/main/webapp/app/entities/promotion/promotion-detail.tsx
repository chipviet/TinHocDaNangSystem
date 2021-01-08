import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './promotion.reducer';
import { IPromotion } from 'app/shared/model/promotion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPromotionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PromotionDetail = (props: IPromotionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { promotionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.promotion.detail.title">Promotion</Translate> [<b>{promotionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="desciption">
              <Translate contentKey="tinhocdanangApp.promotion.desciption">Desciption</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.desciption}</dd>
          <dt>
            <span id="linkToPromotionalProducts">
              <Translate contentKey="tinhocdanangApp.promotion.linkToPromotionalProducts">Link To Promotional Products</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.linkToPromotionalProducts}</dd>
        </dl>
        <Button tag={Link} to="/promotion" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/promotion/${promotionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ promotion }: IRootState) => ({
  promotionEntity: promotion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PromotionDetail);
